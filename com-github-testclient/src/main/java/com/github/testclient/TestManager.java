package com.github.testclient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.github.testclient.models.TestCase;
import com.github.testclient.models.TestCaseStatus;
import com.github.testclient.ui.LogViewerFrame;
import com.github.testclient.util.AndroidDevice;
import com.github.testclient.util.ReportWriter;

public class TestManager implements Runnable {

	private static HashMap<AndroidDevice, TestManager> m_managerMap = new HashMap<>();
	
	private AndroidDevice m_device;
	private List<TestCase> m_tcList;
	private ConcurrentLinkedDeque<TestCase> m_statusChangeQueue;
	private boolean m_isProcessNext;
	private boolean m_isRunning;
	private TestCase m_activeTestCase;
	private boolean m_removeResults;
	private int m_completedCount;
	private LogViewerFrame logViewer;
	private Logger m_logger = Logger.getLogger("TestManager");

	public void setRemoveResultsFlag(boolean flag)
	{
		m_removeResults = flag;
	}

	public boolean isRunning() {
		return m_isRunning;
	}

	public int getScheduledTestCaseCount()
	{
		return m_tcList.size();
	}
	
	public int getCompletedCount()
	{
		return m_completedCount;
	}
	
	public void setIsProcessNext(boolean isProcessNext) {
		this.m_isProcessNext = isProcessNext;
	}

	private TestManager(AndroidDevice device)
	{
		m_device = device;
		m_tcList = new ArrayList<TestCase>();
		m_statusChangeQueue = new ConcurrentLinkedDeque<>();
		
		logViewer = new LogViewerFrame(m_device);
		logViewer.setLocationRelativeTo(null);
		logViewer.setVisible(false);
	}

	public static TestManager getInstance(AndroidDevice device)
	{
		if(m_managerMap.containsKey(device))
		{
			return m_managerMap.get(device);
		}
		else
		{
			TestManager manager = new TestManager(device);
			m_managerMap.put(device, manager);
			
			return manager;
		}
	}

	public LogViewerFrame getLogViewer()
	{
		return this.logViewer;
	}
	
	public TestCase getActiveTestCase() {
		return m_activeTestCase;
	}

	public TestManager(List<TestCase> list)
	{
		m_tcList = list;
	}

	public void loadTestCase(List<TestCase> list)
	{
		m_tcList.addAll(list);
	}

	public void loadTestCase(TestCase testCase)
	{
		m_tcList.add(testCase);
	}

	public void clearPendingQueue()
	{
		m_tcList.clear();
	}
	
	public boolean isStatusChangeQueueEmpty()
	{
		return m_statusChangeQueue.isEmpty();
	}
	
	public TestCase getTestCaseFromStatusChangeQueue()
	{
		if(!this.isStatusChangeQueueEmpty())
		{
			return m_statusChangeQueue.poll();
		}
		else
		{
			return null;
		}
	}

	public void sortTeseCaseByPriority()
	{
		if(m_tcList.size() > 0)
		{
			List<TestCase> tempList = new ArrayList<TestCase>();
			tempList.add(m_tcList.get(0));
			int index = 0;
			TestCase testCase;
			for(int i = 1; i < m_tcList.size(); i++)
			{
				testCase = m_tcList.get(i);
				for(index = 0; index < tempList.size(); index++)
				{
					if(tempList.get(index).getScriptPriority() > testCase.getScriptPriority())
					{
						break;
					}
				}

				tempList.add(index, testCase);
			}

			// Reset the TestCase List
			m_tcList.clear();
			m_tcList.addAll(tempList);
		}

		for(int i = 0; i < m_tcList.size(); i++)
		{
			m_logger.info("TestCase Name: " + m_tcList.get(i).getScriptName() + " Priority: "  + m_tcList.get(i).getScriptPriority());
		}
	}

	public boolean checkDeviceLock()
	{
		File deviceLock = new File("log" + File.separator 
				+ "Device-" + m_activeTestCase.getDevice().getDeviceName()  + "-" + m_activeTestCase.getDevice().getDeviceID()
				+ File.separator + "device.lck");
		if(deviceLock.exists())
		{
			int removeFlag = JOptionPane.showConfirmDialog(null, "The Device is busy currently, do you want to release the device and start your execution?"
					+ "\nPlease make sure no other session is running on the device before starting new test.", "Release Device", JOptionPane.YES_NO_OPTION);
			if(removeFlag == JOptionPane.YES_OPTION)
			{
				 return deviceLock.delete();
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	
	public void releaseDeviceLock()
	{
		File deviceLock = new File("log" + File.separator 
				+ "Device-" + m_activeTestCase.getDevice().getDeviceName()  + "-" + m_activeTestCase.getDevice().getDeviceID()
				+ File.separator + "device.lck");
		if(deviceLock.exists())
		{
			deviceLock.delete();
		}
	}
	
	public void run()
	{ 
		m_isRunning = true;
		m_isProcessNext = true;
		m_completedCount = 0;
		
		sortTeseCaseByPriority();

		// Set the testcase status to pending
		m_tcList.forEach(tc -> 
		{
			tc.setStatus(TestCaseStatus.Pending);
			m_statusChangeQueue.offer(tc);
		});

		Iterator<TestCase> iterator = m_tcList.iterator();

		int sleepMillisec = 15000;

		logViewer.clearText();
		while(iterator.hasNext() && m_isProcessNext)
		{
			try 
			{
				m_activeTestCase = iterator.next();
				
				if(!checkDeviceLock())
				{
					m_isProcessNext = false;
					continue;
				}
				
				m_activeTestCase.setStatus(TestCaseStatus.Running);
				// Update status change queue
				m_statusChangeQueue.offer(m_activeTestCase);

				if(m_removeResults)
				{
//					m_activeTestCase.clearTestResult();
				}

				Thread executionThread = new Thread(m_activeTestCase);
				executionThread.start();

				Thread.sleep(3000);
				// Update status change queue
				m_statusChangeQueue.offer(m_activeTestCase);
				
				while(executionThread.isAlive() && !m_activeTestCase.isTimeout())
				{
					m_activeTestCase.increaseExecTime(sleepMillisec/1000);
					Thread.sleep(sleepMillisec);
				}

				if(m_activeTestCase.isTimeout())
				{
					stopTestExecution();
					// Set status to Timeout
					m_activeTestCase.setStatus(TestCaseStatus.Timeout);
					m_logger.info("TestCase timeout! Execution time reached: " + m_activeTestCase.getTimeout());
				}
				else
				{
					m_logger.info("Test execution of [" + m_activeTestCase.getScriptName() + "] is completed with status: [" + m_activeTestCase.getStatus().toString() + "]");
				}

				// Update status change queue to final status
				m_statusChangeQueue.offer(m_activeTestCase);
				m_completedCount++;
				
				releaseDeviceLock();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		m_tcList.forEach(tc -> 
		{ 
			if(tc.getStatus() == TestCaseStatus.Pending)
			{
				tc.setStatus(TestCaseStatus.Cancelled);
				// Update status change queue
				m_statusChangeQueue.offer(tc);
				m_completedCount++;
			}
		});
		
		// Stop Batch
		m_isProcessNext = false;
		m_isRunning = false;
		
		// Save Execution Report
		boolean containFailedResult = false;
		ReportWriter rw = new ReportWriter();
		for(TestCase tc : this.m_tcList)
		{
			if(tc.getStatus() == TestCaseStatus.Failed)
			{
				containFailedResult = true;
				break;
			}
		}
		rw.saveReport(containFailedResult, this.m_tcList);
	}

	public void stopTestExecution()
	{
		m_activeTestCase.stopExecution();
		releaseDeviceLock();
	}

	public void stopBatchExecution()
	{
		m_logger.info("Stop batch execution.");
		m_isProcessNext = false;
		stopTestExecution();
	}

}

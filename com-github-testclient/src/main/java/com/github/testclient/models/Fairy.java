package com.github.testclient.models;

import org.apache.log4j.Logger;

import com.github.testclient.TestManager;
import com.github.testclient.ui.LogViewerFrame;
import com.github.testclient.util.AndroidDevice;

public class Fairy implements Runnable {

	private boolean m_stopBuyingFlag;
	private AndroidDevice m_device;
	private TestCase m_activeTestCase;
	private TestManager m_testManager;
	private String m_status;
	private LogViewerFrame logViewer;
	private Logger m_logger = Logger.getLogger("Fairy");


	public Fairy(TestManager tm, AndroidDevice device)
	{
		this.m_testManager = tm;
		this.m_device = device;
		logViewer = new LogViewerFrame();
		logViewer.setLocationRelativeTo(null);
		logViewer.setVisible(false);
	}

	public TestCase getActiveTestCase() {
		return m_activeTestCase;
	}
	
	public LogViewerFrame getLogViewer()
	{
		return this.logViewer;
	}

	public void run()
	{ 
		System.out.println("Fairy service [" + Thread.currentThread().getName() + "] started.");
		
		int sleepMillisec = 15000;
		
		while(!m_stopBuyingFlag)
		{
			if(this.m_testManager.isPendingQueueEmpty())
			{
				m_status = "Pending";
				System.out.println(Thread.currentThread().getName() + " : " + m_status);
				
				// Sleep 10 seconds and check again
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				continue;
			}
			try 
			{
				// Clear Log Viewer
				logViewer.clearText();
				
				m_status = "Running";
				m_activeTestCase = m_testManager.getTestCaseFromPendingQueue();
				
				if(m_activeTestCase == null) continue;
				
				System.out.println("Fairy [" + Thread.currentThread().getName() + "] "
						+ "pick up script: " + m_activeTestCase.getScriptName() 
						+ " with status: " + m_activeTestCase.getStatus());
				
				// Update test case with selected device
				m_activeTestCase.setDevice(m_device);
				
				// Move on to next test case if the current test is not pending
				if(m_activeTestCase.getStatus() != TestCaseStatus.Pending)
				{
					continue;
				}
				
				m_activeTestCase.setStatus(TestCaseStatus.Running);
				// Update status change queue
				updateTestCaseStatus();

				Thread executionThread = new Thread(m_activeTestCase);
				executionThread.start();

				Thread.sleep(3000);
				// Update status change queue
				updateTestCaseStatus();
				
				while(executionThread.isAlive() && !m_activeTestCase.isTimeout())
				{
					m_activeTestCase.increaseExecTime(sleepMillisec/1000);
					Thread.sleep(sleepMillisec);
				}

				if(m_activeTestCase.isTimeout())
				{
					m_activeTestCase.stopExecution();
					// Set status to Timeout
					m_activeTestCase.setStatus(TestCaseStatus.Timeout);
					m_logger.info("TestCase timeout! Execution time reached: " + m_activeTestCase.getTimeout());
				}
				else
				{
					m_logger.info("Test execution of [" + m_activeTestCase.getScriptName() + "] is completed with status: [" + m_activeTestCase.getStatus().toString() + "]");
				}

				// Update status change queue to final status
				updateTestCaseStatus();
				
				this.m_testManager.pushTestCaseToCompletedQueue(m_activeTestCase);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	public void updateTestCaseStatus()
	{
		m_testManager.pushTestCaseToStatusChangeQueue(m_activeTestCase);
	}
	
}
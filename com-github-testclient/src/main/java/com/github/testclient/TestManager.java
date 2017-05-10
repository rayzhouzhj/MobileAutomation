package com.github.testclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;

import com.github.testclient.context.Configurations;
import com.github.testclient.models.Fairy;
import com.github.testclient.models.TestCase;
import com.github.testclient.models.TestCaseStatus;
import com.github.testclient.util.AndroidDevice;

public class TestManager 
{
	private static HashMap<String, TestManager> m_managerMap = new HashMap<>();

	private String m_suiteName;
	private List<AndroidDevice> m_deviceList;
	private List<TestCase> m_tcList;
	private ConcurrentLinkedDeque<TestCase> m_statusChangeQueue;
	private ConcurrentLinkedDeque<TestCase> m_pendingQueue;
	private ConcurrentLinkedDeque<TestCase> m_completedQueue;
	private List<Fairy> m_fairies;
	private Logger m_logger = Logger.getLogger("TestManager");

	public boolean isRunning() {
		return m_tcList.size() != m_completedQueue.size();
	}

	public TestCase getTestCaseByID(int id)
	{
		for(TestCase tc : m_tcList)
		{
			if(tc.getScriptId() == id)
				return tc;
		}
		
		return null;
	}
	
	public int getScheduledTestCaseCount()
	{
		return m_tcList.size();
	}

	private TestManager(String suite)
	{
		m_suiteName = suite;
		
		m_tcList = new ArrayList<TestCase>();
		m_statusChangeQueue = new ConcurrentLinkedDeque<>();
		m_pendingQueue = new ConcurrentLinkedDeque<>();
		m_completedQueue = new ConcurrentLinkedDeque<>();

		m_fairies = new ArrayList<>();
	}
	
	public static TestManager getInstance(String testSuite)
	{
		if(m_managerMap.containsKey(testSuite))
		{
			return m_managerMap.get(testSuite);
		}
		else
		{
			TestManager manager = new TestManager(testSuite);
			m_managerMap.put(testSuite, manager);
			
			return manager;
		}
	}

	public void associateDevices(List<AndroidDevice> list)
	{
		m_deviceList = list;
	}
	
	public void activateFairies()
	{
		if(m_fairies.size() > 0) return;

		// Default fairy number is 5
		int fairiesNum = m_deviceList.size();

		for(int i = 0; i < m_deviceList.size(); i++)
		{
			m_fairies.add(new Fairy(this, m_deviceList.get(i)));
		}

		m_fairies.forEach(buyer -> new Thread(buyer).start());
	}

	public long getCompletedCount()
	{
		return m_tcList.stream()
				.filter(
						tc -> 
						(
								tc.getStatus() != TestCaseStatus.Pending 
								&& tc.getStatus() != TestCaseStatus.Running
						)
					).count();
	}
	
	public long getInProgressCount()
	{
		return m_tcList.stream().filter(tc -> tc.getStatus() == TestCaseStatus.Running).count();
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
		m_completedQueue.clear();
	}

	public boolean isStatusChangeQueueEmpty()
	{
		return m_statusChangeQueue.isEmpty();
	}

	public boolean isPendingQueueEmpty()
	{
		return m_pendingQueue.isEmpty();
	}

	public TestCase getTestCaseFromPendingQueue()
	{
		if(!this.isPendingQueueEmpty())
		{
			return m_pendingQueue.poll();
		}
		else
		{
			return null;
		}
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

	public void pushTestCaseToStatusChangeQueue(TestCase testCase)
	{
		m_statusChangeQueue.offer(testCase);
	}

	public void pushTestCaseToCompletedQueue(TestCase testCase)
	{
		m_completedQueue.offer(testCase);
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

	public void addTestCaseToQueue()
	{ 
		activateFairies();

		sortTeseCaseByPriority();

		// Set the TestCase status to pending
		// Add TestCase to pending queue
		m_tcList.forEach(tc -> 
		{
			tc.setStatus(TestCaseStatus.Pending);
			this.pushTestCaseToStatusChangeQueue(tc);
			m_pendingQueue.offer(tc);
		});
	}

	public void stopTestExecution(List<TestCase> pendingList)
	{
		for(TestCase temp : this.m_tcList)
		{
			for(TestCase pending4Stop : pendingList)
			{
				if(pending4Stop.getScriptId() == temp.getScriptId() 
						&& temp.getStatus() == TestCaseStatus.Running)
				{
					temp.setStatus(TestCaseStatus.Aborted);
					temp.stopExecution();
					this.pushTestCaseToStatusChangeQueue(temp);
					
					break;
				}
				else if(pending4Stop.getScriptId() == temp.getScriptId() 
						&& temp.getStatus() == TestCaseStatus.Pending)
				{
					temp.setStatus(TestCaseStatus.Cancelled);
					this.pushTestCaseToStatusChangeQueue(temp);
					
					break;
				}
			}
		}
	}

	public void stopBatchExecution()
	{
		m_logger.info("Stop batch execution.");
		
		// Mark all pending scripts as cancelled
		this.m_tcList.forEach(tc -> 
		{
			if(tc.getStatus() == TestCaseStatus.Pending)
			{
				tc.setStatus(TestCaseStatus.Cancelled);
				this.pushTestCaseToStatusChangeQueue(tc);
			}
		});

		stopTestExecution(this.m_tcList);
	}
}

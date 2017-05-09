package com.github.testclient.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.text.PlainDocument;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.testclient.models.Timer;
import com.github.testclient.ui.components.DeviceControlPane;
import com.github.testclient.ui.components.filter.IntFilter;

/**
 *
 * @author Ray_Zhou
 */
public class ScheduleControlFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 4420831498398606623L;

	private Timer timer;
	private boolean isScheduled = false;
	private boolean isRunnable = false;

	private TestManagerFrame managerFrame;
	/**
	 * Creates new form SchedulerFrame
	 */
	public ScheduleControlFrame(TestManagerFrame managerFrame) {
		this.managerFrame = managerFrame;

		setTitle("TestManager Scheduler");
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		repreatSchedule = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		scheduleMessage = new javax.swing.JLabel();
		scheduleBtn = new javax.swing.JButton();
		cancelBtn = new javax.swing.JButton();
		closeBtn = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		specificTimeOption = new javax.swing.JRadioButton();
		repeatOption = new javax.swing.JRadioButton();
		frequencyValue = new javax.swing.JTextField();
		frequencyDropdown = new javax.swing.JComboBox<>();
		startNowCheckbox = new javax.swing.JCheckBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		jLabel1.setText("Schedule Start Time:");

		jLabel2.setText("Schedule End Time:");

		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
		scheduleStartDate = new DatePicker(dateSettings);
		scheduleStartDate.setDate(LocalDate.now());

		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings.setFormatForDisplayTime(DateTimeFormatter.ofPattern("hh:mm a"));
		scheduleStartTime = new TimePicker(timeSettings);
		scheduleStartTime.setTime(LocalTime.now());

		dateSettings = new DatePickerSettings();
		dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
		scheduleEndDate = new DatePicker(dateSettings);
		scheduleEndDate.setDate(LocalDate.now());

		timeSettings = new TimePickerSettings();
		timeSettings.setFormatForDisplayTime(DateTimeFormatter.ofPattern("hh:mm a"));
		scheduleEndTime = new TimePicker(timeSettings);
		scheduleEndTime.setTime(LocalTime.now());

		scheduleMessage.setText("");

		scheduleBtn.setText("Schedule");

		cancelBtn.setText("Cancel");

		closeBtn.setText("Close");

		scheduleBtn.setText("Schedule");
		scheduleBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				scheduleBtnActionPerformed(evt);
			}
		});

		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelBtnActionPerformed(evt);
			}
		});

		closeBtn.setText("Close");
		closeBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBtnActionPerformed(evt);
			}
		});

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Start at:"));

		repreatSchedule.add(specificTimeOption);
		specificTimeOption.setText("Specific Time:");
		specificTimeOption.setSelected(true);

		timeSettings = new TimePickerSettings();
		timeSettings.setFormatForDisplayTime(DateTimeFormatter.ofPattern("hh:mm a"));
		specificTriggerTime = new TimePicker(timeSettings);
		specificTriggerTime.setTime(LocalTime.of(10, 0));

		repreatSchedule.add(repeatOption);
		repeatOption.setText("Start and repeat every ");

		frequencyValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		frequencyValue.setText("1");
		PlainDocument doc = (PlainDocument) frequencyValue.getDocument();
		doc.setDocumentFilter(new IntFilter());

		frequencyDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Days", "Hours", "Minutes" }));

		startNowCheckbox.setText("Start 1st round now?");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addComponent(repeatOption)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(frequencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(frequencyDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addComponent(specificTimeOption)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(specificTriggerTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(startNowCheckbox)))
						.addContainerGap(78, Short.MAX_VALUE))
				);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(specificTimeOption)
								.addComponent(specificTriggerTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(startNowCheckbox))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(repeatOption)
								.addComponent(frequencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(frequencyDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGap(30, 30, 30)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
										.addGap(18, 18, 18)
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addComponent(scheduleStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(scheduleStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addComponent(scheduleEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(scheduleEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addComponent(scheduleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addComponent(cancelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addComponent(closeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(91, 91, 91))
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(scheduleMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addContainerGap(24, Short.MAX_VALUE))))
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1)
								.addComponent(scheduleStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(scheduleStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel2)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(scheduleEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(scheduleEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scheduleMessage)
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(scheduleBtn)
								.addComponent(cancelBtn)
								.addComponent(closeBtn))
						.addContainerGap(27, Short.MAX_VALUE))
				);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				);

		pack();
	}// </editor-fold>                        

	private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
		this.setVisible(false);
	}                                        

	private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) { 
		if(isScheduled)
		{
			int result = JOptionPane.showConfirmDialog(null, "Do you want to cancel current Schedule?", "Cancel Schedule", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.NO_OPTION) return;

			timer.stopTimer();
			this.updateScheduleMessage("Last schedule stopped at: " + LocalDateTime.now(), true);
		}
		
		isScheduled = false;
		this.scheduleBtn.setEnabled(true);
	}                                      

	private void copyToDevices()
	{
		int tabCount = this.managerFrame.getTabbedPane().getTabCount();

		for(int index = 0; index < tabCount; index++)
		{
			SchedulerFrame scheduler = ((DeviceControlPane)this.managerFrame.getTabbedPane().getComponentAt(index)).getScheduler();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run()
				{
					scheduler.setScheduleStartDate(ScheduleControlFrame.this.scheduleStartDate.getDate());
					scheduler.setScheduleStartTime(ScheduleControlFrame.this.scheduleStartTime.getTime());
					scheduler.setScheduleEndDate(ScheduleControlFrame.this.scheduleEndDate.getDate());
					scheduler.setScheduleEndTime(ScheduleControlFrame.this.scheduleEndTime.getTime());

					if(ScheduleControlFrame.this.specificTimeOption.isSelected())
					{
						scheduler.getSpecificTimeOption().setSelected(true);
						scheduler.setSpecificTriggerTime(ScheduleControlFrame.this.specificTriggerTime.getTime());
					}
					else
					{
						scheduler.getRepeatOption().setSelected(true);
						scheduler.getFrequencyValue().setText(ScheduleControlFrame.this.frequencyValue.getText());
						scheduler.getFrequencyDropdown().setSelectedItem(ScheduleControlFrame.this.frequencyDropdown.getSelectedItem());
					}

					scheduler.setScheduled(true);
					scheduler.getScheduleBtn().setEnabled(false);
				}
			});

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void scheduleBtnActionPerformed(java.awt.event.ActionEvent evt) {

		if(isScheduled == false)
		{
			String scheduledStartDate = this.scheduleStartDate.getText();
			String scheduledStartTime = this.scheduleStartTime.getText().toUpperCase();
			String scheduledStartDateTime = scheduledStartDate + " " + scheduledStartTime;
			String scheduledEndDate = this.scheduleEndDate.getText();
			String scheduledEndTime = this.scheduleEndTime.getText().toUpperCase();
			String scheduledEndDateTime = scheduledEndDate + " " + scheduledEndTime;
			String specificTime = this.specificTriggerTime.getText().toUpperCase();
			boolean isStartNow = this.startNowCheckbox.isSelected();
			boolean isBySpecificTime = this.specificTimeOption.isSelected();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
			LocalDateTime startDate = LocalDateTime.parse(scheduledStartDateTime, formatter);
			LocalDateTime endDate = LocalDateTime.parse(scheduledEndDateTime, formatter);

			if(endDate.isBefore(startDate))
			{
				JOptionPane.showMessageDialog(null, "Schedule End Date should not be earlier than Schedule Start Date!");
				
				return;
			}
			
			if(isBySpecificTime)
			{
				LocalTime triggerTime = LocalTime.parse(specificTime, DateTimeFormatter.ofPattern("hh:mm a"));

				timer = new Timer(startDate, endDate, isBySpecificTime, triggerTime, isStartNow);
			}
			else
			{
				String timeSpan = this.frequencyValue.getText().trim();
				String timeUnit = this.frequencyDropdown.getSelectedItem().toString().toUpperCase();

				timer = new Timer(startDate, endDate, isBySpecificTime, Integer.parseInt(timeSpan), TimeUnit.valueOf(timeUnit));
			}

			this.copyToDevices();

			this.activateSchedule(timer);

			isScheduled = true;
			this.scheduleBtn.setEnabled(false);
		}
	}

	private void activateSchedule(Timer timer)
	{
		new Thread(()->
		{
			timer.updateNextScheduledTime();

			while(isScheduled)
			{
				if(!timer.isTimerRunning())
				{
					System.out.println("Timer is stopped.");
					isScheduled = false;
					break;
				}

				// Update Next Running Session Time
				this.updateScheduleMessage("Next session will start at: " + timer.getNextScheduledTime().toString(), false);

				timer.waitForNextAlerting();

				// If schedule is cancelled
				if(!isScheduled)
				{
					break;
				}

				// Start Execution
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						ScheduleControlFrame.this.startExecution();
					}
				});

				timer.updateNextScheduledTime();
				if(!timer.isTimerRunning())
				{
					isScheduled = false;
				}
			}

			// Update message and enable Schedule Button
			this.updateScheduleMessage("Last session started at: " + LocalDateTime.now().toString(), true);
		}).start();
	}

	public void updateScheduleMessage(String message, boolean enableScheduleButton)
	{
		// Update message for current frame
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ScheduleControlFrame.this.setScheduleMessage(message);
				ScheduleControlFrame.this.scheduleBtn.setEnabled(enableScheduleButton);
			}
		});

		// Update message for all tabs
		new Thread(()->
		{
			int tabCount = this.managerFrame.getTabbedPane().getTabCount();

			for(int index = 0; index < tabCount; index++)
			{
				SchedulerFrame scheduler = ((DeviceControlPane)this.managerFrame.getTabbedPane().getComponentAt(index)).getScheduler();
				if(scheduler.isScheduled())
				{
					// Update Next Running Session Time
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							scheduler.setScheduleMessage(message);
							scheduler.getScheduleBtn().setEnabled(enableScheduleButton);
						}
					});
				}
			}
		}).start();
	}

	public void setScheduleMessage(String message)
	{
		this.scheduleMessage.setText(message);
	}

	private void startExecution()
	{
		new Thread(()->
		{
			int tabCount = this.managerFrame.getTabbedPane().getTabCount();

			for(int index = 0; index < tabCount; index++)
			{
				SchedulerFrame scheduler = ((DeviceControlPane)this.managerFrame.getTabbedPane().getComponentAt(index)).getScheduler();
				if(scheduler.isScheduled())
				{
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							scheduler.startExecution();
						}
					});
				}
			}
		}).start();

	}


	// Variables declaration - do not modify                     
	private javax.swing.JButton cancelBtn;
	private javax.swing.JButton closeBtn;
	private javax.swing.JComboBox<String> frequencyDropdown;
	private javax.swing.JTextField frequencyValue;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private DatePicker scheduleStartDate;
	private DatePicker scheduleEndDate;
	private TimePicker specificTriggerTime;
	private TimePicker scheduleStartTime;
	private TimePicker scheduleEndTime;
	private javax.swing.JRadioButton repeatOption;
	private javax.swing.ButtonGroup repreatSchedule;
	private javax.swing.JButton scheduleBtn;
	private javax.swing.JLabel scheduleMessage;
	private javax.swing.JRadioButton specificTimeOption;
	private javax.swing.JCheckBox startNowCheckbox;
	// End of variables declaration                   
}

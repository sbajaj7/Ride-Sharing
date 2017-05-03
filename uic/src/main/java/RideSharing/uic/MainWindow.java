package RideSharing.uic;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.UIManager;

public class MainWindow {

	private JFrame frmDynamicRidesharing;
	private JTextField txtWaitTime;
	private JTextField shareabltyTxtFld;
	private JTextField numReqTxt;
	private JTextField sharedReqTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmDynamicRidesharing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frmDynamicRidesharing = new JFrame();
		frmDynamicRidesharing.setTitle("Dynamic Ridesharing 1.0");
		frmDynamicRidesharing.setBounds(100, 100, 916, 618);
		frmDynamicRidesharing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDynamicRidesharing.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel fldsPanel = new JPanel();
		frmDynamicRidesharing.getContentPane().add(fldsPanel, BorderLayout.NORTH);
		fldsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblPoolSrc = new JLabel("Pool Size (Sec): ");
		lblPoolSrc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fldsPanel.add(lblPoolSrc);

		final JComboBox poolSizeCmb = new JComboBox();
		poolSizeCmb.setFont(new Font("Tahoma", Font.PLAIN, 15));
		poolSizeCmb.setModel(new DefaultComboBoxModel(new String[] { "30", "60", "120", "180", "240", "300", "360" }));
		poolSizeCmb.setSelectedIndex(0);
		fldsPanel.add(poolSizeCmb);

		JLabel label = new JLabel("New");
		label.setForeground(SystemColor.menu);
		fldsPanel.add(label);

		JLabel lblWaitingTime = new JLabel("Waiting Time:");
		lblWaitingTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fldsPanel.add(lblWaitingTime);

		txtWaitTime = new JTextField();
		txtWaitTime.setSelectionColor(UIManager.getColor("Button.focus"));
		txtWaitTime.setForeground(UIManager.getColor("ComboBox.selectionBackground"));
		txtWaitTime.setEnabled(false);
		txtWaitTime.setText("5 Minutes");
		txtWaitTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtWaitTime.setColumns(10);
		fldsPanel.add(txtWaitTime);

		JLabel label_1 = new JLabel("New");
		label_1.setForeground(SystemColor.menu);
		fldsPanel.add(label_1);

		JLabel lblDelay = new JLabel("Delay(mins):");
		lblDelay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fldsPanel.add(lblDelay);

		final JComboBox cmbDelay = new JComboBox();
		cmbDelay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cmbDelay.setModel(new DefaultComboBoxModel(new String[] { "2", "4", "6", "8", "10" }));
		cmbDelay.setSelectedIndex(0);
		fldsPanel.add(cmbDelay);

		JLabel lblNewLabel = new JLabel("New");
		lblNewLabel.setForeground(SystemColor.control);
		fldsPanel.add(lblNewLabel);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fldsPanel.add(btnSubmit);

		double[] xData = new double[] { 2,4,6,8,10 };
		double[] yData = new double[] { 61,65,69,74,76 };

		// Create Chart
		XYChart chart = QuickChart.getChart("Sample Chart", "Delay(Minutes)", "Percentage Shareability", "Delay v/s Percentage Shareability", xData, yData);

		final JPanel pnlChart = new XChartPanel(chart);
		pnlChart.setVisible(false);

		frmDynamicRidesharing.getContentPane().add(pnlChart, BorderLayout.CENTER);
		
				final Panel resultsPanel = new Panel();
				resultsPanel.setVisible(false);
				frmDynamicRidesharing.getContentPane().add(resultsPanel, BorderLayout.SOUTH);
				btnSubmit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String delay=(String)cmbDelay.getSelectedItem();
						String pool_size=(String)poolSizeCmb.getSelectedItem();
						pnlChart.setVisible(true);
						resultsPanel.setVisible(true);
						try {
							String result=MainClass.initiator(delay,pool_size);
							StringTokenizer tokenizer= new StringTokenizer(result,",");
							String [] strArr= new String[3];
							int i=0;
							while(tokenizer.hasMoreTokens()){
								strArr[i++]=tokenizer.nextToken();
							}
							shareabltyTxtFld.setText(strArr[0]);
							numReqTxt.setText(strArr[1]);
							sharedReqTxt.setText(strArr[2]);
							
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}							}
				});
						resultsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				
						Label PercShareability = new Label("Percentage Shareability:");
						PercShareability.setAlignment(Label.CENTER);
						PercShareability.setFont(new Font("Dialog", Font.PLAIN, 15));
						resultsPanel.add(PercShareability);
						
								shareabltyTxtFld = new JTextField();
								shareabltyTxtFld.setMinimumSize(new Dimension(3, 20));
								shareabltyTxtFld.setFont(new Font("Tahoma", Font.PLAIN, 15));
								shareabltyTxtFld.setEnabled(false);
								shareabltyTxtFld.setColumns(10);
								resultsPanel.add(shareabltyTxtFld);
								
								Label numRequests = new Label("Number of Requests");
								numRequests.setFont(new Font("Dialog", Font.PLAIN, 15));
								numRequests.setAlignment(Label.CENTER);
								resultsPanel.add(numRequests);
								
								numReqTxt = new JTextField();
								numReqTxt.setFont(new Font("Tahoma", Font.PLAIN, 15));
								numReqTxt.setEnabled(false);
								numReqTxt.setColumns(10);
								resultsPanel.add(numReqTxt);
								
								Label sharedReq = new Label("Shared Requests:");
								sharedReq.setFont(new Font("Dialog", Font.PLAIN, 15));
								sharedReq.setAlignment(Label.CENTER);
								resultsPanel.add(sharedReq);
								
								sharedReqTxt = new JTextField();
								sharedReqTxt.setFont(new Font("Tahoma", Font.PLAIN, 15));
								sharedReqTxt.setEnabled(false);
								sharedReqTxt.setColumns(10);
								resultsPanel.add(sharedReqTxt);

	}

}

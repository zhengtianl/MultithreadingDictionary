package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientUI {

	private JFrame frame;
	private JTextField wordText;
	private JTextField meaningText;
	static JTextArea textArea;
	static JTextArea statusArea;
	
	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		statusArea = new JTextArea();
		statusArea.setLineWrap(true);
		statusArea.setBounds(48, 133, 327, 24);
		frame.getContentPane().add(statusArea);
		
		textArea = new JTextArea();
		textArea.setBounds(0,56,434,70);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
		
		JLabel label_title = new JLabel("Muliti- threads-app");
		label_title.setFont(new Font("宋体", Font.BOLD, 16));
		label_title.setBounds(112, 10, 199, 21);
		frame.getContentPane().add(label_title);
		
		JLabel lblWords = new JLabel("Words");
		lblWords.setFont(new Font("宋体", Font.BOLD, 14));
		lblWords.setBounds(32, 167, 62, 15);
		frame.getContentPane().add(lblWords);
		
		JLabel lblMeaning = new JLabel("Meaning");
		lblMeaning.setFont(new Font("宋体", Font.BOLD, 14));
		lblMeaning.setBounds(32, 211, 54, 15);
		frame.getContentPane().add(lblMeaning);
		
		wordText = new JTextField();
		wordText.setBounds(28, 180, 99, 21);
		frame.getContentPane().add(wordText);
		wordText.setColumns(10);
		
		meaningText = new JTextField();
		meaningText.setBounds(28, 230, 99, 21);
		frame.getContentPane().add(meaningText);
		meaningText.setColumns(10);
		
		JButton butAdd = new JButton("Add");
		butAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText(null);
				statusArea.setText(null);
				
				//get the words and meaning
				String words = wordText.getText();
				String meaning = meaningText.getText();
				
				//catch the error
				if(words.isEmpty()||meaning.isEmpty()) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(butAdd, "please add both of them! please");
					return;
				}else if(words.contains(" ")) {
				    Toolkit.getDefaultToolkit().beep(); // sound beep
				    JOptionPane.showMessageDialog(butAdd, "The word cannot contain spaces.");
				    return;
				}
				
				String request = "ADD" + "/" + words + "/" + meaning + "\n";
				try {
					DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					return;
				}
			}
		});
		butAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		butAdd.setBounds(162, 179, 93, 23);
		frame.getContentPane().add(butAdd);
		
		JButton butDelet = new JButton("Delete");
		butDelet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText(null);
				statusArea.setText(null);
				String words = wordText.getText();
				if(words.equals("")) {
					JOptionPane.showMessageDialog(butDelet, "type something to delete");
					return;
				}
				//if no error
				String request = "DELETE" + "/" + words + "\n";
				try {
					DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				} catch (Exception e3) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(butDelet, "the server is over,you should restart the serve");
					return;
				}
			}
		});
		butDelet.setBounds(294, 179, 93, 23);
		frame.getContentPane().add(butDelet);
		
		JButton butUpdate = new JButton("Update");
		butUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText(null);
				statusArea.setText(null);
				
				//get the words and meaning
				String words = wordText.getText();
				String meaning = meaningText.getText();
				//we test error local it can reduce the load of the serve. 
				if(words.isEmpty()||meaning.isEmpty()) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(butUpdate, "please add both of them! please");
					return;
				}else if(words.contains(" ")) {
				    Toolkit.getDefaultToolkit().beep(); // sound beep
				    JOptionPane.showMessageDialog(butUpdate, "The word cannot contain spaces.");
				    return;
				}
				String request = "UPDATE" + "/" + words + "/" + meaning + "\n";
				try {
					DictionaryClient.writer.write(request);
					DictionaryClient.writer.flush();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					return;
				}
				
			}
		});
		butUpdate.setBounds(162, 229, 93, 23);
		frame.getContentPane().add(butUpdate);
		
		JButton btnNewButton = new JButton("Query");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			textArea.setText(null);
			statusArea.setText(null);
			String words = wordText.getText();
			if(words.equals("")) {
				JOptionPane.showMessageDialog(butAdd, "i can not understand your meaning");
				return;
			}
			String request = "QUERY" + "/" + words + "\n";
			try {
				DictionaryClient.writer.write(request);
				DictionaryClient.writer.flush();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				return;
			}
			}
		});
		btnNewButton.setBounds(294, 229, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("meaning board");
		lblNewLabel.setFont(new Font("仿宋", Font.PLAIN, 16));
		lblNewLabel.setBounds(144, 32, 156, 24);
		frame.getContentPane().add(lblNewLabel);
		
		
	}
}

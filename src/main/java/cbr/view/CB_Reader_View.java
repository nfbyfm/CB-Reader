package cbr.view;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cbr.controller.CB_Reader_Controller;


public class CB_Reader_View extends JFrame{

	/**
	 * MainWindow of the Comic-Book-Reader
	 */
	private static final long serialVersionUID = -6804062852707834161L;
	private CB_Reader_Controller controller;
	
	public CB_Reader_View(CB_Reader_Controller maincontroller)
	{
		super("Comic Book-Reader");



		if(maincontroller != null)
			this.controller=maincontroller;

		//set Look & Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(600, 400);
		try
		{
			this.setIconImage(readImageIcon("cbr/images/cbr.png").getImage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				controller.gui_close_program();
			}
		});

		
		create_Menu();
		create_mainpanel();

	}



	private void create_Menu()
	{
		JMenuBar bar = new JMenuBar();

		JMenu menu = new JMenu("File");
		bar.add(menu);


		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		open.setIcon(readImageIcon("cbr/images/open.png"));
		menu.add(open);

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//open cbr-file
				controller.gui_open();
			}
		});

			

		menu.add(new JSeparator());

		JMenuItem close = new JMenuItem("Close");
		close.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		close.setIcon(readImageIcon("cbr/images/exit.png"));
		menu.add(close);

		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//close program
				controller.gui_close_program();
			}
		});

		

		//Extras
		JMenu extras = new JMenu("Extras");
		bar.add(extras);

		JMenuItem info = new JMenuItem("Info");
		info.setIcon(readImageIcon("cbr/images/information.png"));
		extras.add(info);

		//show simple about-dialog
		info.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CB_Reader_AboutDialog f = new CB_Reader_AboutDialog(new JFrame(),readImageIcon("cbr/images/cbr.png").getImage());
				f.show();
			}
		});
		
		extras.add(new JSeparator());
		JMenuItem help = new JMenuItem("Help");
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		help.setIcon(readImageIcon("cbr/images/help.png"));
		extras.add(help);

		help.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Desktop.isDesktopSupported())
				{
					try {
						Desktop.getDesktop().browse(new URI("https://github.com/nfbyfm/CB-Reader/wiki"));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Couldn't open wiki-Page.");
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Couldn't open wiki-Page.");
			}
		});
		

		// set menubar for Mainwindow
		this.setJMenuBar(bar);
	}
	
	
	private void create_mainpanel()
	{
		
	}
	
	
	//get picture from ressources/images-package
	static ImageIcon readImageIcon(String filename) 
	{
		String fileNamePath=  filename;
		URL url= CB_Reader_View.class.getClassLoader().getResource(fileNamePath);
		if(url!= null) {
			ImageIcon icon= new ImageIcon(url);
			return icon;
		} else{ System.err.println("Couldn't find file: "+ fileNamePath); }
		return null;
	}
}

package cbr.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cbr.controller.CB_Reader_Controller;



public class CB_Reader_View extends JFrame{

	/**
	 * MainWindow of the Comic-Book-Reader
	 */
	private static final long serialVersionUID = -6804062852707834161L;



	/*
	 * GUI-Elements
	 */
	private JLabel img_label;
	private JSplitPane splitpane;
	private JList<String> image_list;

	/*
	 * Private Variables
	 */
	private CB_Reader_Controller controller;
	private String current_picture_path;
	private String window_base_titlestring = "Comic Book-Reader - ";

	private Timer recalculateTimer;
	private ActionListener resize_listener;

	//private JList<String> image_list;

	public CB_Reader_View(CB_Reader_Controller maincontroller)
	{
		super("Comic Book-Reader");

		//create listener so the loaded image doesn't get resized every time the "
		resize_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//resize the image / comic book page
				update_image();
				//System.out.println("resize event called");
			}
		};

		//Timer for resizing
		recalculateTimer= new Timer(500, resize_listener);
		recalculateTimer.setRepeats(false);


		if(maincontroller != null)
			this.controller=maincontroller;

		current_picture_path=null;

		//Listener for key commands
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {

				int keyCode = e.getKeyCode();
				switch( keyCode ) { 

				case KeyEvent.VK_ENTER:
					controller.gui_next_picture();
					break;
				case KeyEvent.VK_BACK_SPACE:
					controller.gui_previous_picture();
					break;
				}

			}
		});


		//set Look & Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 
		catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}

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

		//create splitpanel for list of images and Image-Panel
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitpane.setLeftComponent(create_list_view());
		splitpane.setRightComponent(create_imagepanel());
		splitpane.setDividerLocation(170);

		this.add(splitpane);


		//this.add(create_imagepanel());
	}


	//Panel mit Listview erstellen
	private JPanel create_list_view()
	{
		JPanel mainpanel2 = new JPanel();
		mainpanel2.setLayout(new BorderLayout());
		mainpanel2.setBackground(Color.WHITE);
		mainpanel2.setOpaque(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(true);
		scrollPane.setBackground(Color.WHITE);


		image_list = new JList<String>(controller.gui_getModel());

		scrollPane.setViewportView(image_list);		
		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				boolean adjust = e.getValueIsAdjusting();
				if (!adjust) {
					controller.gui_selected_image_changed(image_list.getSelectedIndex());
				}
			}


		};
		image_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		image_list.addListSelectionListener(listSelectionListener);
		image_list.setBorder(new BevelBorder(BevelBorder.LOWERED));


		mainpanel2.add(scrollPane,BorderLayout.CENTER);
		return mainpanel2;
	}

	private JPanel create_imagepanel()
	{
		JPanel retpanel = new JPanel();
		retpanel.setLayout(new BorderLayout());
		retpanel.setBackground(Color.WHITE);
		retpanel.setOpaque(true);



		img_label = new JLabel(readImageIcon("cbr/images/cbr_text.png"));
		img_label.setBackground(Color.WHITE);
		img_label.setOpaque(true);

		JScrollPane pictureScrollPane = new JScrollPane(img_label);//picture);
		pictureScrollPane.setBackground(Color.WHITE);


		retpanel.add(pictureScrollPane);

		//add resize Listener -> resize Image
		retpanel.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {}

			@Override
			public void componentResized(ComponentEvent e) {
				if ( recalculateTimer.isRunning() ){
					recalculateTimer.restart();
				} else {
					recalculateTimer.start();
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentHidden(ComponentEvent e) {}
		});


		return retpanel;
	}



	//function for the controller
	public void show_image(String imagepath)
	{
		if(imagepath != null)
		{
			File fi = new File(imagepath);
			if(fi.exists()==false)
				System.out.println("Viewer: Image-File "+ imagepath + " does not exists.");
			else
			{
				//display image
				current_picture_path=imagepath;
				update_image();
				//System.out.println("Viewer: Display Image with path: " + imagepath);
			}

		}
		else
			System.out.println("Viewer: Imagepath is empty.");
	}

	//private function -> gets called from controller (via show image) or windowresize
	private void update_image()
	{
		if(current_picture_path!=null)
		{
			if(current_picture_path !="" )
			{
				ImageIcon ico = new ImageIcon(current_picture_path);

				//scale image
				int hoehe=this.getHeight()-50;

				Image img = ico.getImage();

				int newwidth = img.getWidth(null);
				newwidth = newwidth*hoehe/img.getHeight(null);

				Image newimg = img.getScaledInstance(newwidth, hoehe, java.awt.Image.SCALE_SMOOTH);

				img_label.setIcon(new ImageIcon(newimg));
				this.setTitle(window_base_titlestring + current_picture_path.substring(current_picture_path.lastIndexOf("\\")+1));

			}
			//else
				//System.out.println("Viewer loading Image: filename is empty.");
		}
		//else
			//System.out.println("Viewer loading Image: no image file selected.");

	}


	//get picture from ressources/images-package
	static ImageIcon readImageIcon(String filename) 
	{
		URL url= CB_Reader_View.class.getClassLoader().getResource(filename);
		if(url!= null) {
			ImageIcon icon= new ImageIcon(url);
			return icon;
		} else{ System.err.println("Couldn't find file: "+ filename); }

		return null;
	}
}

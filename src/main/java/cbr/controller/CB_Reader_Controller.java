package cbr.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import cbr.modell.CB_Reader_Modell;
import cbr.view.CB_Reader_View;


public class CB_Reader_Controller {

	private CB_Reader_View mainview;
	private CB_Reader_Modell mainmodell;
	private DefaultListModel<String> listModel;
	
	public CB_Reader_Controller() {
		mainview = new CB_Reader_View(this);
		mainmodell = null;
	}

	public void showview()
	{
		mainview.setVisible(true);
	}

	public void open_file(String filename)
	{
		File fs = new File(filename);
		if(fs.exists()){
			if( mainmodell != null)
			{
							
				try {
					mainmodell.delete_files();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				
				mainmodell = new CB_Reader_Modell(filename);
				fill_listModel();
				mainview.show_image(mainmodell.get_image_at(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else{
			JOptionPane.showMessageDialog(null, "No matching file with the selected name could be found.","open cbr-file",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void gui_close_program() {
		// TODO Auto-generated method stub
		if(mainmodell !=null)
		{
			try {
				mainmodell.delete_files();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("closing program");
			System.exit(0);
			
		}
		else {
			System.out.println("closing program");
			System.exit(0);
		}
			
		/*int dialogResult = JOptionPane.showConfirmDialog (null, "Really close program?","Close program",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			System.exit(0);
		}*/	
	}

	public void gui_open() {
		// open cbr/cbz-file
		//show FileChooser
		JFileChooser ficho = new JFileChooser();
		ficho.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter imageFilter =new FileNameExtensionFilter("cb*-file", "cbz", "cbr");
		ficho.setFileFilter(imageFilter);
		ficho.setAcceptAllFileFilterUsed(false);
		ficho.setDialogTitle("Open Comic-Book-file");
		ficho.setCurrentDirectory(new File(System.getProperty("user.home") ));

		int returnVal = ficho.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			open_file(ficho.getSelectedFile().getAbsolutePath());							
		}
	}


	public void gui_next_picture()
	{
		if(mainmodell !=null)
			mainview.show_image(mainmodell.get_next_image());
	}

	public void gui_previous_picture()
	{
		if(mainmodell !=null)
			mainview.show_image(mainmodell.get_previous_image());

	}

	public DefaultListModel<String> gui_getModel() {
		// TODO Auto-generated method stub
		
		listModel = new DefaultListModel<>();
		
		if(mainmodell !=null)
		{
			fill_listModel();
		}
		
		return listModel;
	}
	
	
	private void fill_listModel()
	{
		if(mainmodell != null && listModel != null)
		{
			listModel.clear();
			
			
			List <String> img = mainmodell.get_image_list();
			
			if(img != null)
			{
				if(img.isEmpty()==false)
				{
					for (int i = 0; i < img.size(); i++) {
						String fina = img.get(i);
						fina = fina.substring(fina.lastIndexOf("\\")+1);
						listModel.addElement(fina);
					}
				}
				else
					System.out.println("Controller: Imagelist from Model is empty.");
			}
			else
				System.out.println("Controller: Imagelist from Model is null.");
		}
		//System.out.println("ListModel and/or MainModel equal null.");
	}

	public void gui_selected_image_changed(int selectedIndex) {
		// TODO Auto-generated method stub
		if(mainmodell !=null)
			mainview.show_image(mainmodell.get_image_at(selectedIndex));
	}
}



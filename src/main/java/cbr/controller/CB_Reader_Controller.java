package cbr.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cbr.view.CB_Reader_View;


public class CB_Reader_Controller {

	private CB_Reader_View mainview;
	
	public CB_Reader_Controller() {
		mainview = new CB_Reader_View(this);
	}

	public void showview()
	{
		mainview.setVisible(true);
	}

	public void gui_close_program() {
		// TODO Auto-generated method stub
		System.exit(0);
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
		FileFilter imageFilter =new FileNameExtensionFilter("cbz-file", "cbz");
		ficho.setFileFilter(imageFilter);
		ficho.setAcceptAllFileFilterUsed(false);
		ficho.setDialogTitle("open cbz-file");
		ficho.setCurrentDirectory(new File(System.getProperty("user.home") ));

		int returnVal = ficho.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = ficho.getSelectedFile().getAbsolutePath();
			if(filename.toLowerCase().contains(".cbz")==false){
				filename += ".cbz";
			}
			File fs = new File(filename);
			if(fs.exists()){
				//unzip
				//Unzip unzi = new Unzip(filename, null);
				
				File tempfolder = null;
				try {
					tempfolder = createTempDirectory();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(tempfolder !=null)
				{
					Unzip unzi = new Unzip(filename, tempfolder.getAbsolutePath());
					try {
						Desktop.getDesktop().open(tempfolder);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			else{
				JOptionPane.showMessageDialog(null, "No matching file with the selected name could be found.","open cbr-file",JOptionPane.ERROR_MESSAGE);
			}					
		}
	}
	
	
	
	public static File createTempDirectory() throws IOException
	{
	    final File temp;
	    temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

	    if(!(temp.delete()))
	    	throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
	    

	    if(!(temp.mkdir()))
	    	throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
	    

	    return (temp);
	}
}

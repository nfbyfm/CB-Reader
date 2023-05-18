package cbr.modell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class CB_Reader_Modell {

	private List<String> images;
	private int index;
	private File tempfolder;
	
	public CB_Reader_Modell(String filename) throws Exception
	{
		//unzip / unrar file into temp-folder
		//fill array
		//set index to 1
		//unzip
		//Unzip unzi = new Unzip(filename, null);
		
		if(!(filename.toLowerCase().endsWith(".cbz") || filename.toLowerCase().endsWith(".cbr")))
			throw new Exception("CB-Reader: Model-Module: filename doesn't have cbr or cbz-suffix.");
		
			
		
		tempfolder = null;
		
		
		try {
			tempfolder = createTempDirectory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(tempfolder !=null)
		{
			//check wheather it's a cbz or cbr-File
			if(filename.toLowerCase().endsWith(".cbz"))
			{
				//unzip
				Unzip unzi = new Unzip(filename, tempfolder.getAbsolutePath());
				images = new ArrayList<String>();
				images= unzi.get_image_paths();
				return;
			}
			
			
			if(filename.toLowerCase().endsWith(".cbr"))
			{
				Unrar unra = null;
				//unrar
				try {
					unra = new Unrar(filename, tempfolder.getAbsolutePath());
					
				} catch (Exception e) {
					// show error message
					JOptionPane.showMessageDialog(null, "Could not open cbr-file. Maybe file is encrypted or currupt?","Error loading cbr-file",JOptionPane.WARNING_MESSAGE);
				}
				
				if(unra.equals(null)== false)
				{
					images = new ArrayList<String>();
					images= unra.get_image_paths();
					return;
				}
				
				
				
			}
			
			/*
			try {
				Desktop.getDesktop().open(tempfolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}
	
	public String get_image_at(int image_index)
	{
		if(!images.isEmpty())
		{
			if(image_index >=0 && image_index < images.size())
			{
				return images.get(image_index);
			}
			else
			{
				System.out.println("Modell: Image-Index (" + image_index + ") is out of range. Array-Size: " + images.size());
				return null;
			}
				
		}
		else
		{
			System.out.println("Modell: images-Array is empty.");
			return null;
		}
			
	}
	
	public String get_next_image()
	{
		if(index <0)
		{
			return null;			
		}
		else
		{
			index +=1;
			if(index>= images.size())
				index=0;
			return images.get(index);
		}
	}
	
	public String get_previous_image()
	{
		if(index<0)
		{
			return null;
		}
		else
		{
			index -=1;
			if(index <0)
				index=images.size()-1;
			return images.get(index);
		}
	}
	
	public void delete_files() throws IOException
	{
		images.clear();
		index = -1;
		
		if(tempfolder.exists())
		{
			//try to delete the temp-folder
			FileUtils.deleteDirectory(tempfolder);
			//System.out.println(tempfolder + " has been deleted.");
			
		}
			
	}
	
	private static File createTempDirectory() throws IOException
	{
	    final File temp;
	    temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

	    if(!(temp.delete()))
	    	throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
	    

	    if(!(temp.mkdir()))
	    	throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
	    

	    return (temp);
	}

	public List<String> get_image_list() {
		// TODO Auto-generated method stub
		return images;
	}
	
}

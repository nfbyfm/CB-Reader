package cbr.modell;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import cbr.controller.Unzip;

public class CB_Reader_Modell {

	private String[] images;
	private int index;
	private File tempfolder;
	
	public CB_Reader_Modell(String filename)
	{
		//unzip / unrar file into temp-folder
		//fill array
		//set index to 1
		//unzip
		//Unzip unzi = new Unzip(filename, null);
		
		tempfolder = null;
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
	
	public String get_next_image()
	{
		if(index <0)
		{
			return null;			
		}
		else
		{
			index +=1;
			if(index>= images.length)
				index=0;
			return images[index];
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
				index=images.length-1;
			return images[index];
		}
	}
	
	public void delete_files() throws IOException
	{
		Arrays.fill(images, null);
		index = -1;
		
		if(tempfolder.exists())
		{
			//try to delete it
			if(!(tempfolder.delete()))
		    	throw new IOException("Could not delete temp file: " + tempfolder.getAbsolutePath());
			
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
	
}

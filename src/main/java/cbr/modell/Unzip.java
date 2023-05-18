package cbr.modell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;

public class Unzip {

	private List<String> images;

	@SuppressWarnings("deprecation")
	public Unzip(String zipfilename, String targetfolder)
	{

		if(targetfolder== null)
		{
			//create new "temp"-directory in the same folder as the zip-file 
			String zipfilefolder = new File(zipfilename).getAbsolutePath();
			targetfolder = zipfilefolder.substring(0,zipfilefolder.lastIndexOf("\\")+1) + "temp\\";
		}


		System.out.println("unzipping into " + targetfolder);

		try {
			// Open the zip file
			ZipInputStream zis = null;
			try {

				zis = new ZipInputStream(new FileInputStream(zipfilename));
				ZipEntry entry;
				
				images = new ArrayList<String>();
				
				while ((entry = zis.getNextEntry()) != null) {

					// Create a file on HDD in the destinationPath directory
					// destinationPath is a "root" folder, where you want to extract your ZIP file
					File entryFile = new File(targetfolder, entry.getName());
					if (entry.isDirectory()) {

						if (entryFile.exists()) {
							System.out.println("Directory {0} already exists!" + entryFile.getName());
						} else {
							entryFile.mkdirs();
						}

					} else {

						// Make sure all folders exists (they should, but the safer, the better ;-))
						if (entryFile.getParentFile() != null && !entryFile.getParentFile().exists()) {
							entryFile.getParentFile().mkdirs();
						}

						// Create file on disk...
						if (!entryFile.exists()) {
							entryFile.createNewFile();
							images.add(entryFile.getAbsolutePath());
							
						}

						// and rewrite data from stream
						OutputStream os = null;
						try {
							os = new FileOutputStream(entryFile);
							IOUtils.copy(zis, os);
						} finally {
							IOUtils.closeQuietly(os);
						}
					}
				}
				System.out.println("File has been unzipped.");
			} finally {
				IOUtils.closeQuietly(zis);
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public List<String> get_image_paths()
	{
		return images;
	}


}

package cbr.modell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

public class Unrar {

	private List<String> images;
	
	public Unrar(String archive, String destination) throws Exception {
		if (archive == null || destination == null) {
			throw new RuntimeException("archive and destination must me set");
		}
		images = new ArrayList<String>();
		
		File arch = new File(archive);
		if (!arch.exists()) {
			throw new RuntimeException("the archive does not exit: " + archive);
		}
		File dest = new File(destination);
		if (!dest.exists() || !dest.isDirectory()) {
			throw new RuntimeException(
					"the destination must exist and point to a directory: "
							+ destination);
		}
		extractArchive(arch, dest);
		System.out.println("cbr has been extracted.");
		/*
		System.out.println("Unrared image-paths:");
		for (int i = 0; i < images.size(); i++) {
			System.out.println(images.get(i));
		}
		*/
	}



	private void extractArchive(File archive, File destination) {
		Archive arch = null;
		try {
			arch = new Archive(archive);
		} catch (RarException e) {
			System.err.println(e);
		} catch (IOException e1) {
			System.err.println(e1);
		}
		if (arch != null) {
			if (arch.isEncrypted()) {
				System.out.println("archive is encrypted cannot extract");
				return;
			}
			FileHeader fh = null;
			while (true) {
				fh = arch.nextFileHeader();
				if (fh == null) {
					break;
				}
				if (fh.isEncrypted()) {
					System.out.println("file is encrypted cannot extract: "	+ fh.getFileNameString());
					continue;
				}
				//System.out.println("extracting: " + fh.getFileNameString());
				try {
					if (fh.isDirectory()) {
						createDirectory(fh, destination);
					} else {
						File f = createFile(fh, destination);
						OutputStream stream = new FileOutputStream(f);
						arch.extractFile(fh, stream);
						stream.close();
						images.add(f.getAbsolutePath());
					}
				} catch (IOException e) {
					System.err.println("error extracting the file" + e.getMessage());
				} catch (RarException e) {
					System.err.println("error extraction the file" + e.getMessage());
				}
			}
		}
	}

	private static File createFile(FileHeader fh, File destination) {
		File f = null;
		String name = null;
		if (fh.isFileHeader() && fh.isUnicode()) {
			name = fh.getFileNameW();
		} else {
			name = fh.getFileNameString();
		}
		f = new File(destination, name);
		if (!f.exists()) {
			try {
				f = makeFile(destination, name);
			} catch (IOException e) {
				System.err.println("error creating the new file: " + f.getName() + e.getMessage());
			}
		}
		return f;
	}

	private static File makeFile(File destination, String name)
			throws IOException {
		String[] dirs = name.split("\\\\");
		if (dirs == null) {
			return null;
		}
		String path = "";
		int size = dirs.length;
		if (size == 1) {
			return new File(destination, name);
		} else if (size > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				path = path + File.separator + dirs[i];
				new File(destination, path).mkdir();
			}
			path = path + File.separator + dirs[dirs.length - 1];
			File f = new File(destination, path);
			f.createNewFile();
			return f;
		} else {
			return null;
		}
	}

	private static void createDirectory(FileHeader fh, File destination) {
		File f = null;
		if (fh.isDirectory() && fh.isUnicode()) {
			f = new File(destination, fh.getFileNameW());
			if (!f.exists()) {
				makeDirectory(destination, fh.getFileNameW());
			}
		} else if (fh.isDirectory() && !fh.isUnicode()) {
			f = new File(destination, fh.getFileNameString());
			if (!f.exists()) {
				makeDirectory(destination, fh.getFileNameString());
			}
		}
	}

	private static void makeDirectory(File destination, String fileName) {
		String[] dirs = fileName.split("\\\\");
		if (dirs == null) {
			return;
		}
		String path = "";
		for (String dir : dirs) {
			path = path + File.separator + dir;
			new File(destination, path).mkdir();
		}

	}



	public List<String> get_image_paths() {
		// return list of images
		return images;
	}
}

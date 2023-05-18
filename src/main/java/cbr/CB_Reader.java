package cbr;

import cbr.controller.CB_Reader_Controller;

public class CB_Reader {

	public static void main(String[] args) {
		
		CB_Reader_Controller controller = new CB_Reader_Controller();
		controller.showview();
		if (args.length == 1) 
			controller.open_file(args[0]);
	}

}

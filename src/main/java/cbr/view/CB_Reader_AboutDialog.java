package cbr.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class CB_Reader_AboutDialog extends JDialog{
	/**
	 * creates and shows About-Dialog
	 */
	private static final long serialVersionUID = -5247252524677914650L;

	

	public CB_Reader_AboutDialog(JFrame parent, Image appimage) {
		// create window and show it
		super(parent, "About CB-Reader", true);

		Color backgroundcolor = Color.WHITE;

		this.setBackground(backgroundcolor);

		//show program-Icon
		if (appimage != null){

			this.setIconImage(appimage);

			JLabel imglabel = new JLabel("");

			int newwidth = appimage.getWidth(null);
			newwidth = newwidth*100/appimage.getHeight(null);

			Image newimg = appimage.getScaledInstance(newwidth, 100, java.awt.Image.SCALE_SMOOTH);
			imglabel.setIcon(new ImageIcon(newimg));
			imglabel.setBackground(backgroundcolor);
			Border border = imglabel.getBorder();
			Border margin = new EmptyBorder(5,10,5,10);
			imglabel.setBorder(new CompoundBorder(border, margin));
			getContentPane().add(imglabel,"West");
		}



		Box b = Box.createVerticalBox();
		b.setBackground(backgroundcolor);
		b.add(Box.createGlue());
		b.add(new JLabel("simple Comic Book-Reader"));
		b.add(new JLabel("written in Java by nfbyfm"));
		b.add(new JLabel(" "));
		b.add(new JLabel("Use: "));
		b.add(new JLabel(" press Enter for next page"));
		b.add(new JLabel(" press Backspace for previous page"));
		b.add(new JLabel(" "));
		b.add(Box.createGlue());
		getContentPane().add(b,"Center");

		JPanel p2 = new JPanel();
		JButton b_close = new JButton("Close");
		p2.add(b_close);
		getContentPane().add(p2, "South");

		b_close.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
		setSize(350, 200);
	}
}

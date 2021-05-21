import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageEditorGUI1 {

	private static JFrame frame;
	private static String pathToImage, ext = ".jpg";
	private static JLabel picture;
	private static BufferedImage img = null, img2 = null, backup = null;
	private static int width, height, radius = 5;

	public static void main(String[] args) {
		frame = new JFrame("SuhPhotoshop");
		JPanel panel = new JPanel();
		Color myColor = new Color(56, 56, 57);
		panel.setBackground(myColor);
		UIDefaults uiDefaults = UIManager.getDefaults();
		JMenuItem importItem, saveItem, exitItem, restoreItem, horiFItem, vertiFItem, grayItem, sepiaItem, invertItem,
				gaussianItem, buldgeItem, cropItem, resizeItem;
		picture = new JLabel(new ImageIcon(""));
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenu menu1 = new JMenu("Options");
		importItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save As");
		exitItem = new JMenuItem("Exit");

		cropItem = new JMenuItem("Crop");
		resizeItem = new JMenuItem("Resize");
		restoreItem = new JMenuItem("Restore to Original");
		horiFItem = new JMenuItem("Horizontal Flip");
		vertiFItem = new JMenuItem("Vertical Flip");
		grayItem = new JMenuItem("Gray Scale");
		sepiaItem = new JMenuItem("Sepia Tone");
		invertItem = new JMenuItem("Invert Colour");
		gaussianItem = new JMenuItem("Gaussian Blur");
		buldgeItem = new JMenuItem("Buldge Effect");

		menu1.add(restoreItem);
		menu1.add(horiFItem);
		menu1.add(vertiFItem);
		menu1.add(grayItem);
		menu1.add(sepiaItem);
		menu1.add(invertItem);
		menu1.add(gaussianItem);
		menu1.add(buldgeItem);
		menu1.add(cropItem);
		menu1.add(resizeItem);

		importItem.setMnemonic(KeyEvent.VK_O);
		saveItem.setMnemonic(KeyEvent.VK_S);
		exitItem.setMnemonic(KeyEvent.VK_E);
		cropItem.setMnemonic(KeyEvent.VK_C);
		resizeItem.setMnemonic(KeyEvent.VK_T);
		restoreItem.setMnemonic(KeyEvent.VK_R);
		horiFItem.setMnemonic(KeyEvent.VK_H);
		vertiFItem.setMnemonic(KeyEvent.VK_V);
		grayItem.setMnemonic(KeyEvent.VK_G);
		sepiaItem.setMnemonic(KeyEvent.VK_P);
		invertItem.setMnemonic(KeyEvent.VK_I);
		gaussianItem.setMnemonic(KeyEvent.VK_U);
		buldgeItem.setMnemonic(KeyEvent.VK_B);

		KeyStroke keyImport = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		importItem.setAccelerator(keyImport);
		KeyStroke keySave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		saveItem.setAccelerator(keySave);
		KeyStroke keyExit = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		exitItem.setAccelerator(keyExit);
		KeyStroke keyCrop = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		cropItem.setAccelerator(keyCrop);
		KeyStroke keyResize = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK);
		resizeItem.setAccelerator(keyResize);
		KeyStroke keyRestore = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
		restoreItem.setAccelerator(keyRestore);
		KeyStroke keyHori = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
		horiFItem.setAccelerator(keyHori);
		KeyStroke keyVerti = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
		vertiFItem.setAccelerator(keyVerti);
		KeyStroke keyGray = KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK);
		grayItem.setAccelerator(keyGray);
		KeyStroke keySepia = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
		sepiaItem.setAccelerator(keySepia);
		KeyStroke keyInvert = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
		invertItem.setAccelerator(keyInvert);
		KeyStroke keyGauss = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);
		gaussianItem.setAccelerator(keyGauss);
		KeyStroke keyBulge = KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK);
		buldgeItem.setAccelerator(keyBulge);

		menu.add(importItem);
		menu.add(saveItem);
		menu.add(exitItem);
		menuBar.add(menu);
		menuBar.add(menu1);

		resizeItem.addActionListener(new Menu2Listener());
		cropItem.addActionListener(new Menu2Listener());
		importItem.addActionListener(new MenuListener());
		saveItem.addActionListener(new MenuListener());
		exitItem.addActionListener(new MenuListener());
		restoreItem.addActionListener(new Menu2Listener());
		horiFItem.addActionListener(new Menu2Listener());
		vertiFItem.addActionListener(new Menu2Listener());
		grayItem.addActionListener(new Menu2Listener());
		sepiaItem.addActionListener(new Menu2Listener());
		invertItem.addActionListener(new Menu2Listener());
		gaussianItem.addActionListener(new Menu2Listener());
		buldgeItem.addActionListener(new Menu2Listener());

		panel.add(picture);

		frame.setVisible(true);
		frame.setSize(1185, 810);
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
	}

	public static BufferedImage makeCopy(BufferedImage b) {
		ColorModel cmod = b.getColorModel();
		boolean isAlphaPremultiplied = cmod.isAlphaPremultiplied();
		WritableRaster ras = b.copyData(null);
		return new BufferedImage(cmod, ras, isAlphaPremultiplied, null);
	}

	public static double gaussianEquation(double x, double y, double vari) {
		return (1 / (2 * Math.PI * Math.pow(vari, 2))
				* Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(vari, 2))));
	}

	public static double[][] weightMatrix(int radius, double vari) {
		double[][] values = new double[radius][radius];
		double sum = 0;
		// first iteration
		for (int j = 0; j < values.length; j++) {
			for (int i = 0; i < values[j].length; i++) {
				values[j][i] = gaussianEquation(j - radius / 2, i - radius / 2, vari);
				sum += values[j][i];
			}
		}
		// Normalize data
		for (int j = 0; j < values.length; j++) {
			for (int i = 0; i < values[j].length; i++) {
				values[j][i] /= sum;
			}
		}
		return values;
	}

	public static int sumColor(double[][] blurredColors) {
		int sum = 0;
		for (int i = 0; i < blurredColors.length; i++) {
			for (int j = 0; j < blurredColors[i].length; j++) {
				sum += blurredColors[i][j];
			}
		}
		return sum;
	}

	private static class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String event = e.getActionCommand();
			if (event.equals("Open")) {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = chooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						File selectedFile = chooser.getSelectedFile();
						img = ImageIO.read(selectedFile);
						img2 = makeCopy(img);
						backup = makeCopy(img);
						width = img.getTileWidth();
						height = img.getTileHeight();
						pathToImage = selectedFile.getAbsolutePath();
						try {
							ext = pathToImage.substring(pathToImage.lastIndexOf(".") + 1);
						} catch (Exception d) {
						}
					} catch (IOException d) {
					}

					picture.setIcon(new ImageIcon(img));
				}
			} else if (event.equals("Save As")) {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = chooser.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						File selectedFile = chooser.getSelectedFile();
						selectedFile = new File(selectedFile.getAbsolutePath() + "." + ext);
						ImageIO.write(img, ext, selectedFile);
					} catch (IOException d) {
					}
				}
			} else if (event.equals("Exit")) {
				System.exit(0);
			}
		}
	}

	private static class Menu2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String event = e.getActionCommand();
			int pixelV;
			if (event.equals("Restore to Original")) {
				img = makeCopy(backup);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Horizontal Flip")) {
				int x2 = 0;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						x2 = width - x - 1;
						pixelV = img.getRGB(x2, y);
						img2.setRGB(x, y, pixelV);
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));

			} else if (event.equals("Vertical Flip")) {
				int y2 = 0;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						y2 = height - y - 1;
						pixelV = img.getRGB(x, y2);
						img2.setRGB(x, y, pixelV);
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Gray Scale")) {
				int r, g, b;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						Color rgb = new Color(img.getRGB(x, y));
						r = (int) (rgb.getRed() * 0.299);
						g = (int) (rgb.getGreen() * 0.587);
						b = (int) (rgb.getBlue() * 0.114);
						Color newRgb = new Color(r + g + b, r + g + b, r + g + b);
						img2.setRGB(x, y, newRgb.getRGB());
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Sepia Tone")) {
				int r, g, b, r2, g2, b2;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						Color rgb = new Color(img.getRGB(x, y));
						r = (int) rgb.getRed();
						g = (int) rgb.getGreen();
						b = (int) rgb.getBlue();
						r2 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
						g2 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
						b2 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

						r = (r2 > 255) ? 255 : r2;
						g = (g2 > 255) ? 255 : g2;
						b = (b2 > 255) ? 255 : b2;

						Color newRgb = new Color(r, g, b);
						img2.setRGB(x, y, newRgb.getRGB());
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Invert Colour")) {
				int r, g, b;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						Color rgb = new Color(img.getRGB(x, y));
						r = 255 - ((int) rgb.getRed());
						g = 255 - ((int) rgb.getGreen());
						b = 255 - ((int) rgb.getBlue());

						Color newRgb = new Color(r, g, b);
						img2.setRGB(x, y, newRgb.getRGB());
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Gaussian Blur")) {
				double[][] values = weightMatrix(5, 1.5);
				for (int x = 0; x < width - radius; x++) {
					for (int y = 0; y < height - radius; y++) {
						double[][] blurRed = new double[radius][radius];
						double[][] blurGreen = new double[radius][radius];
						double[][] blurBlue = new double[radius][radius];
						
						for (int valueX = 0; valueX < values.length; valueX++) {
							for (int valueY = 0; valueY < values[valueX].length; valueY++) {
								try {
									int tempX = valueX + x - (values.length / 2);
									int tempY = valueX + y - (values.length / 2);

									double currentValue = values[valueX][valueY];

									Color colour = new Color(img.getRGB(tempX, tempY));

									blurRed[valueX][valueY] = colour.getRed() * currentValue;
									blurGreen[valueX][valueY] = colour.getGreen() * currentValue;
									blurBlue[valueX][valueY] = colour.getBlue() * currentValue;
								} catch (Exception d) {
								}
							}
						}
						img2.setRGB(x, y, new Color(sumColor(blurRed), sumColor(blurGreen), sumColor(blurBlue)).getRGB());
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Buldge Effect")) {
				// https://math.stackexchange.com/questions/266250/explanation-of-this-image-warping-bulge-filter-algorithm
				double radius1, a, tempX, tempY, temps, m = 0.00000315;
				int X, Y;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						try {
							radius1 = Math.sqrt(Math.pow(x - (width / 2), 2) + Math.pow(y - (height / 2), 2));
							a = Math.atan2(y - (height / 2), x - (width / 2));
							temps = (Math.pow(radius1, 1.7) / (0.08 * (width / 2 + height / 2)));
							X = (int) (temps * Math.cos(a) + (width / 2));
							Y = (int) (temps * Math.sin(a) + (height / 2));

							img2.setRGB(x, y, img.getRGB(X, Y));
						} catch (Exception h) {
							img2.setRGB(x, y, Color.BLACK.getRGB());
						}
					}
				}
				img = makeCopy(img2);
				picture.setIcon(new ImageIcon(img));
			} else if (event.equals("Crop")) {
				JTextField lengthCrop = new JTextField();
				JTextField heightCrop = new JTextField();
				JComponent[] dimensions = new JComponent[] { new JLabel("Length"), lengthCrop, new JLabel("Height"),
						heightCrop, };
				int dimens = JOptionPane.showConfirmDialog(null, dimensions, "Crop", JOptionPane.PLAIN_MESSAGE);
				if (dimens == JOptionPane.OK_OPTION && Integer.parseInt(lengthCrop.getText()) <= img.getTileWidth()
						&& Integer.parseInt(heightCrop.getText()) <= img.getTileHeight()) {
					BufferedImage crop = new BufferedImage(Integer.parseInt(lengthCrop.getText()),
							Integer.parseInt(heightCrop.getText()), BufferedImage.TYPE_INT_RGB);
					Graphics cropI = crop.getGraphics();
					cropI.drawImage(img, 0, 0, Integer.parseInt(lengthCrop.getText()),
							Integer.parseInt(heightCrop.getText()), 0, 0, Integer.parseInt(lengthCrop.getText()),
							Integer.parseInt(heightCrop.getText()), null);
					cropI.dispose();
					img = makeCopy(crop);
					img2 = makeCopy(crop);
					width = img.getTileWidth();
					height = img.getTileHeight();
					picture.setIcon(new ImageIcon(img));
				} else {
				}
			} else if (event.equals("Resize")) {
				JTextField lengthCrop = new JTextField();
				JTextField heightCrop = new JTextField();
				JComponent[] dimensions = new JComponent[] { new JLabel("Length"), lengthCrop, new JLabel("Height"),
						heightCrop, };
				int dimens = JOptionPane.showConfirmDialog(null, dimensions, "Crop", JOptionPane.PLAIN_MESSAGE);
				if (dimens == JOptionPane.OK_OPTION) {
					Image temp = img.getScaledInstance(Integer.parseInt(lengthCrop.getText()),
							Integer.parseInt(heightCrop.getText()), Image.SCALE_SMOOTH);
					BufferedImage resized = new BufferedImage(Integer.parseInt(lengthCrop.getText()),
							Integer.parseInt(heightCrop.getText()), BufferedImage.TYPE_INT_RGB);
					Graphics2D resizedI = resized.createGraphics();
					resizedI.drawImage(temp, 0, 0, null);
					resizedI.dispose();
					img = makeCopy(resized);
					img2 = makeCopy(resized);
					width = img.getTileWidth();
					height = img.getTileHeight();
					picture.setIcon(new ImageIcon(img));
				} else {
				}
			}
		}
	}
}

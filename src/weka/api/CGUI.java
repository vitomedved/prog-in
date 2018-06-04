package weka.api;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;

import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import javax.swing.event.ListSelectionEvent;

/* za pocetak, zao mi je sta kod ima milijun linija al ljepse izgleda gui nego prije.
 * ako nekome nesto treba u kodu ili za kod nek me pita jer ce se sam izgubit
 * Vito - tebi sam napisala komentar oko 620e linije
 * i jos jednom zao mi je ako kod izgleda uzasno i ako je uzasno nekvalitetno napisan
 * al ja javu ne znam i ovo je bilo najbolje sta sam mogla i znala*/


public class CGUI extends JFrame {

	JFileChooser fileC = new JFileChooser();
	String path = null;
	String path2 = null;
	String imeDat = "";
	String imeDat2 = "";
	String ispis = "";
	String ispis2 = "";
	String poruka = "";
	Object[] odbKlasifikatori;
	Object odbPodaci;
	String odbKlasifikatoris;
	String odbPodacis;
	String putPod ="";
	String[] josjednavarijabla;
	List<String> klas_lista = new ArrayList<String>();
	
	JTextArea textArea;
	JList list;
	JList list_1;
	DefaultListModel listModel = new DefaultListModel();
	DefaultListModel listModel1 = new DefaultListModel();
	JTextPane textPane_1;
	JTextPane textPane;
	private JPanel rangClassifiers;
	private JPanel mainGUI;
	private JPanel addData;
	private JPanel viewData;
	private JPanel addClassifier;
	private JPanel viewClassifier;
	private JTextField txtOdabranaDatoteka;
	private JTextField txtOdabranaDatoteka_1;

	/**
	 * Create the frame.
	 */
	public CGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 358);
		getContentPane().setLayout(new CardLayout(0, 0));
		
//_________________________________izbornik_______________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
		
		mainGUI = new JPanel();
		getContentPane().add(mainGUI, "Izbornik");
		mainGUI.setLayout(null);
		mainGUI.setVisible(true);
		
		JButton btnPregledKlasifikatora = new JButton("Pregled klasifikatora");
		btnPregledKlasifikatora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewClassifier.setVisible(true);
				mainGUI.setVisible(false);
				textPane_1.setText("");
				if(ispis_pod() == true) {
					textPane_1.setText(ispis2);

				}
			}
		});
		btnPregledKlasifikatora.setBounds(12, 13, 653, 36);
		mainGUI.add(btnPregledKlasifikatora);
		
		JButton btnPregledPodataka = new JButton("Pregled podataka");
		btnPregledPodataka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				viewData.setVisible(true);
				mainGUI.setVisible(false);
				textPane.setText("");
				if(ispis_pod() == true) {
			
					textPane.setText(ispis);

				}
				

			}
		});
		btnPregledPodataka.setBounds(12, 76, 653, 36);
		mainGUI.add(btnPregledPodataka);
		
		JButton btnDodajNoviPodatak = new JButton("Dodaj novi podatak");
		btnDodajNoviPodatak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addData.setVisible(true);
				mainGUI.setVisible(false);
			}
		});
		btnDodajNoviPodatak.setBounds(12, 262, 653, 36);
		mainGUI.add(btnDodajNoviPodatak);
		
		JButton btnDodajNoviKlasifikator = new JButton("Dodaj novi klasifikator");
		btnDodajNoviKlasifikator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addClassifier.setVisible(true);
				mainGUI.setVisible(false);
				
			}
		});
		btnDodajNoviKlasifikator.setBounds(12, 200, 653, 36);
		mainGUI.add(btnDodajNoviKlasifikator);
		
		JButton btnRangiranjeKlasifikatora = new JButton("Rangiranje klasifikatora");
		btnRangiranjeKlasifikatora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mainGUI.setVisible(false);
				rangClassifiers.setVisible(true);
				listModel.removeAllElements();
				listModel1.removeAllElements();
				klas_lista.clear();
				
				ispis_pod();
				
				
			}
		});
		btnRangiranjeKlasifikatora.setBounds(12, 139, 653, 36);
		mainGUI.add(btnRangiranjeKlasifikatora);
		
//_______________________dodavanje podataka_________________________________________
//__________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
		
		addData = new JPanel();
		getContentPane().add(addData, "Dodavanje novih podataka");
		addData.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(12, 226, 408, 25);
		addData.add(label);
		addData.setVisible(false);
		
		txtOdabranaDatoteka = new JTextField();
		txtOdabranaDatoteka.setEditable(false);
		txtOdabranaDatoteka.setText("*Odabrana datoteka");
		txtOdabranaDatoteka.setBounds(12, 85, 408, 35);
		addData.add(txtOdabranaDatoteka);
		txtOdabranaDatoteka.setColumns(10);
		
		JButton btnOdaberiDatoteku = new JButton("Dodaj podatak");
		btnOdaberiDatoteku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileC.setCurrentDirectory(new File(System.getProperty("user.home")));
				if(e.getSource()==btnOdaberiDatoteku) {
					int res = fileC.showOpenDialog(fileC);
					
					if(res==JFileChooser.APPROVE_OPTION){
						File file = fileC.getSelectedFile();
						path = file.getAbsolutePath();
						imeDat = file.getName();
						
						if(imeDat.endsWith(".arff")) {
							imeDat2 = imeDat.substring(0,imeDat.length()-5);
							imeDat2 = imeDat2.substring(0, 1).toUpperCase() + imeDat2.substring(1);

						}
						
						
						if(path.endsWith(".arff")) {
							txtOdabranaDatoteka.setText(path);
						}else {
							path = null;
							label.setText("Krivi nastavak!");
						}
					

						
					
					}
				}
			}
		});
		btnOdaberiDatoteku.setBounds(12, 13, 169, 40);
		addData.add(btnOdaberiDatoteku);
		
		JButton btnSpremi = new JButton("Spremi");
		btnSpremi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(path == null){
					label.setText("Odaberi datoteku!");
					
				}else {
					
						Path src = Paths.get(path);
						Path dst = Paths.get("D:\\xampp\\htdocs\\prog-ing\\datasets\\" + imeDat);
						path2 = dst.toString().replace("\\", "\\\\");
											
						try {
							System.out.println(path2);

							if(upis_pod() == true) {
								Files.copy(src, dst);
								label.setText("Uspješno izvršeno");
					
							}else {
								label.setText(poruka);

							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
						}

					
				}
			}
		});
		btnSpremi.setBounds(12, 144, 195, 40);
		addData.add(btnSpremi);
		
		JButton btnPoniti = new JButton("Poni\u0161ti");
		btnPoniti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtOdabranaDatoteka.setText("");
				label.setText("");
			}
		});
		btnPoniti.setBounds(225, 144, 195, 40);
		addData.add(btnPoniti);
		
		JButton btnNatrag = new JButton("Natrag");
		btnNatrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addData.setVisible(false);
				mainGUI.setVisible(true);
				txtOdabranaDatoteka.setText("");
				label.setText("");
				path = "";
				path2 = "";
				imeDat = "";
				imeDat2 = "";
			}
		});
		btnNatrag.setBounds(568, 273, 97, 25);
		addData.add(btnNatrag);
		
		
//__________________________pregled podataka________________________________________
//__________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________		
		
		viewData = new JPanel();
		getContentPane().add(viewData, "Pregled podataka");
		viewData.setLayout(null);
		
		JLabel lblIspisPodataka = new JLabel("Ispis podataka");
		lblIspisPodataka.setBounds(23, 13, 105, 16);
		viewData.add(lblIspisPodataka);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 42, 388, 227);
		viewData.add(scrollPane);
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		
		JButton btnNatrag_1 = new JButton("Natrag");
		btnNatrag_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
				viewData.setVisible(false);
				mainGUI.setVisible(true);
				
			
			}
		});
		btnNatrag_1.setBounds(568, 273, 97, 25);
		
		viewData.add(btnNatrag_1);
		
		
//_______________________dodavanje klasifikatora___________________________________________________________
//_________________________________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
		
		addClassifier = new JPanel();
		getContentPane().add(addClassifier, "name_579059521456198");
		addClassifier.setLayout(null);
		
		txtOdabranaDatoteka_1 = new JTextField();
		txtOdabranaDatoteka_1.setText("*Odabrana datoteka");
		txtOdabranaDatoteka_1.setEditable(false);
		txtOdabranaDatoteka_1.setBounds(12, 85, 408, 35);
		addClassifier.add(txtOdabranaDatoteka_1);
		txtOdabranaDatoteka_1.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(12, 226, 408, 25);
		addClassifier.add(label_1);
		
		JButton btnNatrag_2 = new JButton("Natrag");
		btnNatrag_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addClassifier.setVisible(false);
				mainGUI.setVisible(true);
				path = "";
				path2 = "";
				imeDat = "";
				imeDat2 = "";
				txtOdabranaDatoteka_1.setText("");
				label_1.setText("");
				
			}
		});
		btnNatrag_2.setBounds(568, 273, 97, 25);
		addClassifier.add(btnNatrag_2);
		
		JButton btnOdaberiDatoteku_1 = new JButton("Dodaj klasifikator");
		btnOdaberiDatoteku_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fileC.setCurrentDirectory(new File(System.getProperty("user.home")));
				if(e.getSource()==btnOdaberiDatoteku_1) {
					int res = fileC.showOpenDialog(fileC);
					
					if(res==JFileChooser.APPROVE_OPTION){
						File file = fileC.getSelectedFile();
						path = file.getAbsolutePath();
						imeDat = file.getName();
						
						if(imeDat.endsWith(".java")) {
							imeDat2 = imeDat.substring(0,imeDat.length()-5);
							imeDat2 = imeDat2.substring(0, 1).toUpperCase() + imeDat2.substring(1);

						}
						
						
						if(path.endsWith(".java")) {
							txtOdabranaDatoteka_1.setText(path);
						}else {
							path = null;
							label_1.setText("Krivi nastavak!");
						}
					}
				}
				
				
				
			}
		});
		btnOdaberiDatoteku_1.setBounds(12, 13, 169, 40);
		addClassifier.add(btnOdaberiDatoteku_1);
		
		JButton btnSpremi_1 = new JButton("Spremi");
		btnSpremi_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(path == null){
					label_1.setText("Odaberi datoteku!");
					
				}else {
					
						Path src = Paths.get(path);
						Path dst = Paths.get("D:\\xampp\\htdocs\\prog-ing\\classifiers\\" + imeDat);
						path2 = dst.toString().replace("\\", "\\\\");
											
						try {
							System.out.println(path2);

							if(upis_pod() == true) {
								Files.copy(src, dst);
								label_1.setText("Uspjesno izvrseno");
					
							}else {
								label_1.setText(poruka);

							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
						}

					
				}
				
				
				
			}
		});
		btnSpremi_1.setBounds(12, 144, 195, 40);
		addClassifier.add(btnSpremi_1);
		
		JButton btnPoniti_1 = new JButton("Poni\u0161ti");
		btnPoniti_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtOdabranaDatoteka_1.setText("");
				label_1.setText("");
				
			}
		});
		btnPoniti_1.setBounds(225, 144, 195, 40);
		addClassifier.add(btnPoniti_1);
		
		
		
//_______________________pregled klasifikatora___________________________________________________________
//_________________________________________________________________________________________________________		
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
		
		
		viewClassifier = new JPanel();
		getContentPane().add(viewClassifier, "name_579103122400931");
		viewClassifier.setLayout(null);
		
		JButton btnNatrag_3 = new JButton("Natrag");
		btnNatrag_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane_1.setText("");
				viewClassifier.setVisible(false);
				mainGUI.setVisible(true);
				
				
				
			}
		});
		btnNatrag_3.setBounds(568, 273, 97, 25);
		viewClassifier.add(btnNatrag_3);
		
		
		JLabel lblIspisKlasifikatora = new JLabel("Ispis klasifikatora");
		lblIspisKlasifikatora.setBounds(12, 13, 109, 16);
		viewClassifier.add(lblIspisKlasifikatora);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 42, 388, 227);
		viewClassifier.add(scrollPane_1);
		
		textPane_1 = new JTextPane();
		scrollPane_1.setViewportView(textPane_1);
		textPane_1.setEditable(false);
		
		
//__________________________________________rangiranje__________________________________________________
//______________________________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
		rangClassifiers = new JPanel();
		getContentPane().add(rangClassifiers, "name_588577219857277");
		rangClassifiers.setLayout(null);
		
		textArea = new JTextArea();

	
		JTextPane txtpnodabraniPodatak = new JTextPane();
		txtpnodabraniPodatak.setEditable(false);
		txtpnodabraniPodatak.setText("*Odabrani podatak");
		txtpnodabraniPodatak.setBounds(359, 42, 306, 43);
		rangClassifiers.add(txtpnodabraniPodatak);
		
		JTextPane txtpnodabraniKlasifikatori = new JTextPane();
		txtpnodabraniKlasifikatori.setText("*Odabrani klasifikatori");
		txtpnodabraniKlasifikatori.setEditable(false);
		txtpnodabraniKlasifikatori.setBounds(359, 98, 306, 43);
		rangClassifiers.add(txtpnodabraniKlasifikatori);
		
		JLabel lblOdaberiPodatak = new JLabel("Odaberi podatak");
		lblOdaberiPodatak.setBounds(12, 13, 99, 16);
		rangClassifiers.add(lblOdaberiPodatak);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 42, 99, 218);
		rangClassifiers.add(scrollPane_2);

		list = new JList(listModel);		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				
				odbPodaci = list.getSelectedValue();
				StringBuilder builder = new StringBuilder();
				builder.append(odbPodaci);
				odbPodacis = builder.toString();
				txtpnodabraniPodatak.setText(odbPodacis);
				
			}
		});
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(list);
		
		JLabel lblOdaberiKlasifikatore = new JLabel("Odaberi klasifikatore");
		lblOdaberiKlasifikatore.setBounds(171, 13, 125, 16);
		rangClassifiers.add(lblOdaberiKlasifikatore);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(169, 42, 127, 218);
		rangClassifiers.add(scrollPane_3);
		
		list_1 = new JList(listModel1);		
		list_1.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("deprecation")
			public void valueChanged(ListSelectionEvent arg0) {
				odbKlasifikatori = list_1.getSelectedValues();

				StringBuilder builder = new StringBuilder();
				for(int i=0; i < odbKlasifikatori.length; i++) {
					builder.append(odbKlasifikatori[i].toString()).append("\n");
					odbKlasifikatoris = builder.toString();
					txtpnodabraniKlasifikatori.setText(odbKlasifikatoris);
				}
				
				
			}
		});
		
		list_1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_3.setViewportView(list_1);
		
		JButton btnNatrag_4 = new JButton("Natrag");
		btnNatrag_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rangClassifiers.setVisible(false);
				mainGUI.setVisible(true);
				
				txtpnodabraniKlasifikatori.setText("");
				txtpnodabraniPodatak.setText("");
				textArea.setText("");
				
				
			}
		});
		btnNatrag_4.setBounds(568, 273, 97, 25);
		rangClassifiers.add(btnNatrag_4);
		
		
		/*________Vito ovo ti je listener za Rangiraj button. 
		 * - klas_lista ti je lista u koju se spremaju pathevi klasifikatora potegnuti iz baze, morala sam napravit ko listu,
		 * jer nisam znala sta drugo, posto klasifikatora moze bit vise jel...
		 * - putPod je obican string u koji se sprema path podatka________
		 * textAreu sam pustila cisto da sebi mogu testirat dal radi ono sta hocu*/
		
		
		JButton btnRangiraj = new JButton("Rangiraj!");
		btnRangiraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					klas_lista.clear();
					izvuci_klasifikator(odbKlasifikatoris);
					izvuci_podatak(odbPodacis);
					textArea.setText(putPod + "\n" + klas_lista);
					
					
					try {
						//Create dataset for BoxPlot
						DefaultBoxAndWhiskerCategoryDataset _dataset = new DefaultBoxAndWhiskerCategoryDataset();
						
						klas_lista.clear();
						klas_lista.add("NaiveBayes");
						klas_lista.add("J48");
						
						for(int i = 0; i < klas_lista.size(); i++)
						{
							//Create and set classifier
							CClassifier classifier = new CClassifier();
							
							// TODO: klas_lista bi trebala sadrzavati string classifiers.ime_datoteke, 
							// a prilikom uploada se mora spremati u C:\Users\vitom\Documents\GitHub\prog-in\src\classifiers, 
							// klasifikator mora biti .java
							//package klasifikatora mora biti classifiers
							classifier.setClassifier("classifiers." + klas_lista.get(i));
							
							//Create list for cross-classification to use later for BoxPlot
							List array = new ArrayList();
							
							DataSource source;
						
							//Get dataset and set its class index
							source = new DataSource(putPod);
							
							Instances dataset = source.getDataSet();
							dataset.setClassIndex(dataset.numAttributes() - 1);
							
							//Get list from cross-classification to use later for BoxPlot
							array = classifier.CrossClassify(dataset, 1, 10);
							
							//Add array to dataset
							_dataset.add(array, classifier.toString(), classifier.toString());
						}
						//Draw BoxPlot
						BoxPlot plot = new BoxPlot("Test", _dataset);
						plot.pack();
						RefineryUtilities.centerFrameOnScreen(plot);
						plot.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					/*CClassifier j48 = new CClassifier();
					j48.setClassifier("weka.classifiers.trees.J48");
					
					CClassifier nb = new CClassifier();
					nb.setClassifier("weka.classifiers.bayes.NaiveBayes");
					
					
					List[] arr = new ArrayList[2];
					arr[0] = new ArrayList();
					arr[1] = new ArrayList();
					
					arr[0] = j48.CrossClassify(dataset, 1, 10);
					arr[1] = nb.CrossClassify(dataset, 1, 10);
					
					DefaultBoxAndWhiskerCategoryDataset m_dataset = new DefaultBoxAndWhiskerCategoryDataset();
					
					m_dataset.add(arr[0], j48.toString(), j48.toString());
					m_dataset.add(arr[1], nb.toString(), nb.toString());
					//BoxAndWhiskerCategoryDataset m_dataset = new DefaultBoxAndWhiskerCategoryDataset();
					
					
					BoxPlot plot = new BoxPlot("Test", m_dataset);
					plot.pack();
					RefineryUtilities.centerFrameOnScreen(plot);
					plot.setVisible(true);*/

			}

			
		});
		btnRangiraj.setBounds(459, 273, 97, 25);
		rangClassifiers.add(btnRangiraj);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(359, 154, 306, 103);
		rangClassifiers.add(scrollPane_4);
		
		scrollPane_4.setViewportView(textArea);
		
		

		viewData.setVisible(false);

	}
	
	
	
//_______________________________________FUNKCIJE ZA UPIS I ISPIS_______________________________________
//______________________________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
//________________________________________________________________________________
	
	
	
	boolean upis_pod() {
		 try { 
	            String url = "jdbc:mysql://localhost:3306/prog-ing"; 
	            Connection conn = DriverManager.getConnection(url,"root","root"); 
	            Statement st = conn.createStatement(); 
	           
	            	
            	if(path2.endsWith(".arff") && path2 != null) {
            		ResultSet provjera = st.executeQuery("SELECT * FROM `javni_pod` WHERE path = '"+ path2 + "' or name = '"+ imeDat2 +"'");
	            	if(!provjera.next()) {
	            		st.executeUpdate("INSERT INTO `javni_pod` (`path`, `name`) " + 
		    	                "VALUES ('" + path2 + "','"+ imeDat2 +"')"); 
			            
			    		return true; 
			    		
	            	}else {
	            		poruka = "Datoteka je već u bazi";
	            		return false;
	            	}
            	}else if(path2.endsWith(".java") && path2 != null) {
            		ResultSet provjera = st.executeQuery("SELECT * FROM `klasifikatori` WHERE path = '"+ path2 + "' or name = '"+ imeDat2 +"'");
	            	if(!provjera.next()) {
	            		st.executeUpdate("INSERT INTO `klasifikatori` (`path`, `name`) " + 
		    	                "VALUES ('" + path2 + "','"+ imeDat2 +"')"); 
			            
			    		return true; 
			    		
	            	}else {
	            		poruka = "Datoteka je već u bazi";
	            		return false;
	            	}
            	}else {
            		return false;
            	}
	            
	        } catch (Exception e) { 
	            System.err.println("Got an exception! "); 
	            System.err.println(e.getMessage()); 
	            poruka = "Neuspijelo spajanje na bazu";
	    		return false; 

	           
	        }
		
		 
	}
	
	
	boolean ispis_pod() {
		try { 
            String url = "jdbc:mysql://localhost:3306/prog-ing"; 
            //Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,"root","root"); 
            
            Statement st1 = conn.createStatement(); 
        	ResultSet provjera = st1.executeQuery("SELECT name FROM `javni_pod`");
        	
        	ispis = "";
        	ispis2 = "";
        	while(provjera.next()) {

        		
        		ispis += provjera.getString("name") + "\n";
        	
        	}
    		
        	List<String> ispis_cisti = Arrays.asList(ispis.split("\\r?\\n"));
        	
        	for(int i = 0; i < ispis_cisti.size(); i++) {
        		listModel.addElement(ispis_cisti.get(i));     		
        	}

        	ResultSet provjera2 = st1.executeQuery("SELECT name FROM `klasifikatori`");
        	while(provjera2.next()) {
        		ispis2 += provjera2.getString("Name") + "\n";
        	}
        	
        	List<String> ispis_cisti2 = Arrays.asList(ispis2.split("\\r?\\n"));
        	
        	for(int i = 0; i < ispis_cisti2.size(); i++) {
        		listModel1.addElement(ispis_cisti2.get(i));     		
        	}
        	

    		

        	conn.close();
	    	return true;      

        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
            poruka = "Neuspijelo spajanje na bazu";
            e.printStackTrace();
    		return false; 

           
        }
		
	}
	
	void izvuci_podatak(String odbPodacis) {
		try {
			String url = "jdbc:mysql://localhost:3306/prog-ing"; 
            Connection conn = DriverManager.getConnection(url,"root","root"); 
            
            Statement st1 = conn.createStatement();
        	ResultSet provjera = st1.executeQuery("SELECT path FROM `javni_pod` where name = '"+ odbPodacis +"' ");
        
        	while(provjera.next()) {

        		putPod = provjera.getString("path");
        	}
            
		}catch (Exception e){
            System.err.println(e.getMessage()); 
		}
		
		
		
	}
	
	void izvuci_klasifikator(String odbKlasifikatoris) {
		
		try {
			String url = "jdbc:mysql://localhost:3306/prog-ing"; 
			
            Connection conn = DriverManager.getConnection(url,"root","root"); 
            
            Statement st1 = conn.createStatement();

            josjednavarijabla =  odbKlasifikatoris.split("\n");
        	
        	for(int i = 0; i < josjednavarijabla.length;i++) {
        		ResultSet provjera2 = st1.executeQuery("SELECT path FROM `klasifikatori` where name = '"+ josjednavarijabla[i] +"' ");
            	
        		while(provjera2.next()) {
        			
        			klas_lista.add(provjera2.getString("path"));

            	}

        	}
        	
    		System.out.println(klas_lista + "\n");

		}catch(Exception e) {
			
			System.out.println(e.getMessage());
		}

	}
}

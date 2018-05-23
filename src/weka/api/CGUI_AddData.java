package weka.api;

import org.eclipse.swt.widgets.Display;
import java.sql.*;

import javax.swing.*;
import java.io.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CGUI_AddData {
	int succ;
	protected Shell shlDodajNoviPodatak;
	JFileChooser fileC = new JFileChooser();
	private Text id;
	String path = null;
	String path2 = null;
	String poruka = "";
	String imeDat = "";
	private Text ime;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void NewWindowAD() {
		try {
			CGUI_AddData window = new CGUI_AddData();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlDodajNoviPodatak.open();
		shlDodajNoviPodatak.layout();
		while (!shlDodajNoviPodatak.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	void createContents() {
		shlDodajNoviPodatak = new Shell();
		shlDodajNoviPodatak.setSize(450, 300);
		shlDodajNoviPodatak.setText("Dodaj novi podatak");
		
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shlDodajNoviPodatak, SWT.H_SCROLL);
		scrolledComposite.setBounds(166, 10, 256, 45);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(composite, SWT.SHADOW_NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 568;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("*odabrana datoteka*");
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnNewButton = new Button(shlDodajNoviPodatak, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileC.setCurrentDirectory(new File(System.getProperty("user.home")));
				if(e.getSource()==btnNewButton) {
					int res = fileC.showOpenDialog(fileC);
					
					if(res==JFileChooser.APPROVE_OPTION){
						File file = fileC.getSelectedFile();
						path = file.getAbsolutePath();
						imeDat = file.getName();
						
						
						if(path.endsWith(".arff")) {
							lblNewLabel.setText(path);
						}else {
							path = null;
							lblNewLabel.setText("Krivi nastavak!");
						}
					

						
					
					}
				}				
			}
			
		});
		btnNewButton.setBounds(10, 10, 90, 30);
		btnNewButton.setText("Odaberi");
		
		id = new Text(shlDodajNoviPodatak, SWT.BORDER);
		id.setBounds(10, 139, 174, 26);
		
		ime = new Text(shlDodajNoviPodatak, SWT.BORDER);
		ime.setBounds(10, 81, 174, 26);
		
		Label lblNapiiIdJavnog = new Label(shlDodajNoviPodatak, SWT.NONE);
		lblNapiiIdJavnog.setBounds(10, 113, 174, 20);
		lblNapiiIdJavnog.setText("Napi\u0161i ID javnog podatka");
		Label label = new Label(shlDodajNoviPodatak, SWT.NONE);
		label.setBounds(10, 218, 307, 20);
		
		Button btnPohrani = new Button(shlDodajNoviPodatak, SWT.NONE);
		btnPohrani.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(path == null){
					label.setText("Odaberi datoteku!");
				}else if(id.getCharCount() == 0) {
					label.setText("Upiši ID javnog podatka!");

				}else if(ime.getCharCount() == 0){
					label.setText("Upiši ime javnog podatka!");
					
				}else {
					
						Path src = Paths.get(path);
						Path dst = Paths.get("C:\\xampp\\htdocs\\javni_podaci\\" + imeDat);
						path2 = dst.toString().replace("\\", "\\\\");
											
						try {
							System.out.println(path2);

							if(upis() == true) {
								Files.copy(src, dst);
								label.setText(poruka);
							}else {
								label.setText(poruka);

							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
						}

					
				}
				
				
			}
		});
		btnPohrani.setBounds(10, 171, 90, 30);
		btnPohrani.setText("Pohrani");
		
		Button btnPonovi = new Button(shlDodajNoviPodatak, SWT.NONE);
		btnPonovi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				label.setText("");
				lblNewLabel.setText("");
				id.setText("");
				ime.setText("");


				
				
			}
		});
		btnPonovi.setBounds(332, 213, 90, 30);
		btnPonovi.setText("Ponovi");
		
		
		Label lblNapiiImeJavnog = new Label(shlDodajNoviPodatak, SWT.NONE);
		lblNapiiImeJavnog.setBounds(10, 55, 192, 20);
		lblNapiiImeJavnog.setText("Napi\u0161i ime javnog podatka");

	}
	
	boolean upis() {
		 try { 
	            String url = "jdbc:mysql://localhost:3306/database?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET"; 
	            Connection conn = DriverManager.getConnection(url,"root",""); 
	            Statement st = conn.createStatement(); 
	            if(path != null) {
	            	ResultSet provjera = st.executeQuery("SELECT * FROM `javni_pod` WHERE path = '"+ path2 + "' or id = '" + id.getText() + "' or name = '"+ ime.getText() +"'");
	            	if(!provjera.next()) {
	            		st.executeUpdate("INSERT INTO `javni_pod` (`ID`, `path`, `name`) " + 
		    	                "VALUES ('" + id.getText() + "','" + path2 + "','"+ ime.getText() +"')"); 
		            	poruka = "Uspješno izvršeno";
			    		return true; 
	            	}else {
	            		poruka = "Datoteka je vec u bazi";
	            		return false;
	            	}
	            	
	            	

	            }else{
	            	return false;
	            }
	            
	           

	        } catch (Exception e) { 
	            System.err.println("Got an exception! "); 
	            System.err.println(e.getMessage()); 
	            poruka = "Neuspijelo spajanje na bazu";
	    		return false; 

	           
	        }
}
}

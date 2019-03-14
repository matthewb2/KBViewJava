package oata;

import javax.swing.JFrame;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class JMenuBarTest extends JFrame implements ActionListener, 
MouseListener, MouseMotionListener, MouseWheelListener,
KeyListener, ListSelectionListener  {
	private JFrame frame;  
    private JMenuItem   open, exit, about, random, deletefile, resize, save, saveas;
    private JMenuItem   fullscreen, delete, previous, next;
    private JFileChooser fc;
    private JLabel jl;
    private JLabel fjl;
    
    boolean fullscreenmode=false;
    
    boolean isChanging = false;
    
    static int xPixel, yPixel; 
    Image myImage;
            
    //String[] fruits;
    //private GraphicsDevice fullscreenDevice;
    
    private Point offset, jlp;
    String path = "E:\\picture2";
    String curfile;
   // File[] fbucket;
    int dir_size;
    List<String> d_bucket = new ArrayList<String>();
    List<String> bucket = new ArrayList<String>();
    int  index;
  
    //Border border = LineBorder.createGrayLineBorder();
    
    BufferedImage readImage = null;
    
    JPopupMenu popup;
    
    JList scrollList;
    JLabel status = new JLabel();
    
    ListSelectionModel listSelectionModel;
    
    DefaultListModel model;

    JScrollPane scrollpanel;
   
    
    public void UpdateJLabel2(int index) {
    	
        //JOptionPane.showMessageDialog(null, index);
            	   
	       	        
        curfile = bucket.get(index);
        try {
			readImage = ImageIO.read(new File(path+"\\"+bucket.get(index)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      	 //jl.setIcon(new ImageIcon(path+"\\"+bucket.get(index)));
    	   Dimension f_size = frame.getContentPane().getSize();
   	 			int w=0,h=0;
 		  	  w  = readImage.getWidth();
 			  h  = readImage.getHeight();
 		   	       	
      	 
      	 if (h >f_size.height) {

      		// JOptionPane.showMessageDialog(null, h);    
          	 Image dimg = readImage.getScaledInstance((int) (w*f_size.height/h), (int)f_size.height, Image.SCALE_SMOOTH);
          	  fjl.setIcon(new ImageIcon(dimg));
      	 } else if (w >f_size.width) {

       		// JOptionPane.showMessageDialog(null, h);    
           	 Image dimg = readImage.getScaledInstance((int) f_size.width, (int)(h*f_size.width/w), Image.SCALE_SMOOTH);
           	  fjl.setIcon(new ImageIcon(dimg));
       	 }
        
   
}
    
   
      
    JMenuBarTest()
    {
        setTitle("Image Viewer");
        
        //setLayout(new BorderLayout());
        setJMenuBarAndMenuBarItems();
        setJFileChooser();
        
        setJLabel();
        
        //setJList();
        
        setAction();
        
        
        // size of frame
        setSize(950,600);
         
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        popup = new JPopupMenu();
	    // add menu items to popup
        delete = new JMenuItem("delete");
        popup.add(delete);
		
        popup.addSeparator();
        
		previous = new JMenuItem("previous");
		popup.add(previous);
		
		next = new JMenuItem("next");
		popup.add(next);
		
		 popup.addSeparator();
		 
		popup.add(new JMenuItem("copy"));
		popup.add(new JMenuItem("paste"));
		    
		 delete.addActionListener(this);
		this.addKeyListener(this);
		 //String[] fruits= {};
		
		
    }
    
   
    
    void setJList( List<String> d_bucket,  List<String> bucket, String curfile)
    {
    		// this.setLayout(new GridLayout(1, 2));;
    	  String[] fruits = new String[bucket.size()];
          fruits = bucket.toArray(fruits);
    		
          String[] d_fruits = new String[d_bucket.size()];
          d_fruits = d_bucket.toArray(d_fruits);
    		
          //JOptionPane.showMessageDialog(null, fruits.length);
    		
    	  this.setLayout(new BorderLayout());
    	  model = new DefaultListModel();
    	  model.removeAllElements();
    	  model.clear();
    	  
    	  dir_size=0;
    	  for (int i=0; i<d_fruits.length; i++) {
    		  
    		  File Fdir = new File(path+"\\"+d_fruits[i]);
            	
            	if (Fdir.isDirectory()) {       
            		Icon ico = FileSystemView.getFileSystemView().getSystemIcon(new File(path+"\\"+d_fruits[i]));
		            model.addElement(new ListEntry(d_fruits[i], ico));
		            dir_size++;
	    		  
            	}
    	  }
    	  
    	  if (fruits.length>0) {
	    	  for (int i=0; i<fruits.length; i++) {
	    		  
	    		  File Fdir = new File(path+"\\"+fruits[i]);
	            	
	            	if (Fdir.isDirectory()) {
	            	//	JOptionPane.showMessageDialog(null, "ddd");
	            	} else {
	    		Icon ico = FileSystemView.getFileSystemView().getSystemIcon(new File(path+"\\"+fruits[i]));
	            	
	                	  
	    		  model.addElement(new ListEntry(fruits[i], ico));
	            	}
	    	  }
    	  }
    	     
    		//JOptionPane.showMessageDialog(null, "ddd");
    	  scrollList = new JList();
    	  scrollList.setModel(model);
    	  scrollList.setCellRenderer(new ListEntryCellRenderer());
    
    	  scrollpanel = new JScrollPane(scrollList);
    	  
    	  // JOptionPane.showMessageDialog(null, "ddd");
    	  scrollpanel.setPreferredSize(new Dimension(200, 500));
    	  scrollpanel.revalidate();
    	  scrollpanel.repaint();
    	  
    	  this.add(scrollpanel, BorderLayout.LINE_START);
    	  
    	  JPanel wimage = new JPanel();
    	  wimage.setLayout(new GridBagLayout());
    	  this.add(wimage);
    	  wimage.add(jl);
    	  
  		  listSelectionModel = scrollList.getSelectionModel();
    	  listSelectionModel.addListSelectionListener(this);
    	  
    		//JOptionPane.showMessageDialog(null, index);
    	  scrollList.addMouseListener(this);
    	 
    	//  JOptionPane.showMessageDialog(null, "ddd");
    	
    	index = bucket.indexOf(curfile);
    	  status_bar(index);
    	//JOptionPane.showMessageDialog(null, curfile);
    	//JOptionPane.showMessageDialog(null, dir_size);
    	//JOptionPane.showMessageDialog(null, index);
    	//JOptionPane.showMessageDialog(null, dir_size);
         
    	scrollList.setSelectionInterval(index+dir_size,  index+dir_size);
    	 //scrollList.setSelectionInterval(9,  9);
    	scrollList.ensureIndexIsVisible(index);

    }
    
    void UpdateJList( List<String> d_bucket,  List<String> bucket, String curfile)
    {
    		// this.setLayout(new GridLayout(1, 2));;
    	  String[] fruits = new String[bucket.size()];
          fruits = bucket.toArray(fruits);
    		
          String[] d_fruits = new String[d_bucket.size()];
          d_fruits = d_bucket.toArray(d_fruits);
    		
          //JOptionPane.showMessageDialog(null, fruits.length);
    		
    	  model.removeAllElements();
    	  model.clear();
    	  
    	  dir_size=0;
    	  for (int i=0; i<d_fruits.length; i++) {
    		  
    		  File Fdir = new File(path+"\\"+d_fruits[i]);
            	
            	if (Fdir.isDirectory()) {       
            		Icon ico = FileSystemView.getFileSystemView().getSystemIcon(new File(path+"\\"+d_fruits[i]));
		            model.addElement(new ListEntry(d_fruits[i], ico));
		            dir_size++;
	    		  
            	}
    	  }
    	  
    	  if (fruits.length>0) {
	    	  for (int i=0; i<fruits.length; i++) {
	    		  
	    		  File Fdir = new File(path+"\\"+fruits[i]);
	            	
	            	if (Fdir.isDirectory()) {
	            	//	JOptionPane.showMessageDialog(null, "ddd");
	            	} else {
	    		Icon ico = FileSystemView.getFileSystemView().getSystemIcon(new File(path+"\\"+fruits[i]));
	            	
	                	  
	    		  model.addElement(new ListEntry(fruits[i], ico));
	            	}
	    	  }
    	  }
    	     
    		//JOptionPane.showMessageDialog(null, "ddd");
    	  
    	  scrollList.setModel(model);
    	  scrollList.setCellRenderer(new ListEntryCellRenderer());
    
    	      	  // JOptionPane.showMessageDialog(null, "ddd");
    	  scrollpanel.setPreferredSize(new Dimension(200, 500));
    	  scrollpanel.revalidate();
    	  scrollpanel.repaint();
    	  
    	    		  
    	//  JOptionPane.showMessageDialog(null, "ddd");
    	
    	index = bucket.indexOf(curfile);
    	  status_bar(index);
    	//JOptionPane.showMessageDialog(null, curfile);
    	//JOptionPane.showMessageDialog(null, dir_size);
    	//JOptionPane.showMessageDialog(null, index);
    	//JOptionPane.showMessageDialog(null, dir_size);
         
    	scrollList.setSelectionInterval(index+dir_size,  index+dir_size);
    	 //scrollList.setSelectionInterval(9,  9);
    	scrollList.ensureIndexIsVisible(index);

    }
  
  
    
    
    
    void setJLabel()
    {
        jl = new JLabel();
        //jl.setText("Left-Bottom");
      
        //this.add(jl, BorderLayout.CENTER);
        Container pane = this.getContentPane();
        
        pane.setLayout(new GridBagLayout()); 
        //pane.setLayout(new BorderLayout());
        //pane.addKeyListener(this);
        
        pane.add(jl);
             
        jl.addKeyListener(this);
        
        jl.addMouseWheelListener(this);
        jl.addMouseListener(this);
        jl.addMouseMotionListener(this);
        
        
        
        JMenuItem item = new JMenuItem("Test1");
        item.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.out.println("Menu item Test1");
          }
        });
     
     
          
    } 
    
  
    void setJMenuBarAndMenuBarItems()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1    = new JMenu("File");
        menu1.setMnemonic('F');
        
        //ImageIcon icon3 = new ImageIcon(getClass().getResource("exit.png"));
        //JMenuItem menu2_1 = new JMenuItem("Sub Menu 2-1",icon3);
        
      //Get file from resources folder
    	// ClassLoader classLoader = getClass().getClassLoader();
    	// File file = new File(classLoader.getResource(fileName).getFile());

     // ImageIcon icon_open = new ImageIcon(getClass().getClassLoader().getResource("res/folder.png"));
        ImageIcon icon_open = new ImageIcon(getClass().getResource("res/folder.png"));
           open     = new JMenuItem("Open", icon_open);
       // open     = new JMenuItem("Open");
        menuBar.add(menu1);
        open.setMnemonic('O');
        menu1.add(open);
        
   //     ImageIcon icon_folder = new ImageIcon(getClass().getResource("res/folder_open.png"));
   //     random     = new JMenuItem("Folder Open", icon_folder);
        random     = new JMenuItem("Folder Open");
        random.setMnemonic('R');
        menu1.add(random);
        
        save     = new JMenuItem("Save");
        save.setMnemonic('S');
        menu1.add(save);
        
        
        saveas     = new JMenuItem("Save As");
        saveas.setMnemonic('A');
        menu1.add(saveas);
        
        
   //     ImageIcon icon_exit = new ImageIcon(getClass().getResource("res/exit.png"));
   //     exit     = new JMenuItem("Exit", icon_exit);
        exit     = new JMenuItem("Exit");
        exit.setMnemonic('X');
        menu1.add(exit);
        
        
        JMenu menu2    = new JMenu("Edit");
        
            
        
        menuBar.add(menu2);
        menu2.setMnemonic('E');
        
        deletefile     = new JMenuItem("Delete File");
        menu2.add(deletefile);
        
        resize     = new JMenuItem("Resize");
        menu2.add(resize);
        
       
        JMenu menu3    = new JMenu("View");
        menu3.setMnemonic('V');
        
        menuBar.add(menu3);
        fullscreen     = new JMenuItem("Full Screen");
        menu3.add(fullscreen);
        JMenu menu4    = new JMenu("Help");
        menu4.setMnemonic('H');
        about     = new JMenuItem("About");
        about.setMnemonic('A');
        menu4.add(about);
        
        menuBar.add(menu4);
        
        setJMenuBar(menuBar);
    }
   
    
    void setJFileChooser()
    {
        fc = new JFileChooser();
        
        fc.setCurrentDirectory(new File(path));  //set default folder
    }
  
    void setAction()
    {
        open.addActionListener(this);
        about.addActionListener(this);
        fullscreen.addActionListener(this);
        random.addActionListener(this);
       // delete.addActionListener(this);
    }
  
    public void actionPerformed(ActionEvent eve)
    {
        int getfile;
        if(eve.getSource() == random) {
        	//JOptionPane.showMessageDialog(null, null);
        	 JFileChooser chooser;
        	   String choosertitle = null;
        	 chooser = new JFileChooser(); 
        	    chooser.setCurrentDirectory(new java.io.File("E:\\"));
        	    chooser.setDialogTitle(choosertitle);
        	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        	    // disable the "All files" option.
        	    //
        	    chooser.setAcceptAllFileFilterUsed(false);
        	    //    
        	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
        	      
        	      File folder = new File(chooser.getSelectedFile().toString());
        	    	File[] listOfFiles = folder.listFiles();
        	    	 	path = chooser.getSelectedFile().toString();
                       //initialize bucket
                       bucket.clear();
                       for (File result : listOfFiles) {
                       	
                       		System.out.println(result.getName());	
                       		bucket.add(result.getName());
                      		
                       
                       }
                       
                       String[] fruits = new String[bucket.size()];
                       fruits = bucket.toArray(fruits);
                    					
						Random generator = new Random(); 
						index = generator.nextInt(bucket.size()) + 1;
					
						curfile = bucket.get(index);
						 
                  	   //  setJList(fruits, index);
                 	    
                 	   
                 	  try {
                          readImage = ImageIO.read(new File(path+"\\"+curfile));
                      	
                          
                      } catch (Exception e) {
                          readImage = null;
                      }         
                 	// FitImage_Frame(index, readImage);
                 	  
                       
        	      }
        	   
            
        	
        }
        if(eve.getSource() == about) {
        	//JOptionPane.showMessageDialog(null, null);
            String html = "<html><body width='%1s'><h1>JKBViewer 0.1</h1>"
                    + "<p>SPACE - show next picture"
                    + "<br>D - delete the current"
                    + "<br>F - view of the full screen mode";
                  
                // change to alter the width 
                
                JOptionPane.showMessageDialog(null, String.format(html, 250, 500));
        }         
        	
        
        if(eve.getSource() == open)
        {
        	 fc.setAccessory(new ImagePreview(fc));
        	getfile = fc.showOpenDialog(rootPane);
                        
                       
            if(getfile==JFileChooser.APPROVE_OPTION)
            {
            	
            	
            	Icon ico = FileSystemView.getFileSystemView().getSystemIcon(new File(fc.getSelectedFile().getPath()));
            	
            	//JOptionPane.showMessageDialog(null, file.getParent());
            	
            	fc.getSelectedFile().getPath();
            	
                jl.setIcon(new ImageIcon(fc.getSelectedFile().getPath()));
               
                File file = new File(fc.getSelectedFile().getPath());
                
                curfile = file.getName();
                
                File tfile = new File(file.getParent());
                
                File[] flist = tfile.listFiles();
                //initialize bucket
                bucket.clear();
                d_bucket.clear();
                                 	
                  	         	  
                
                for (File result : flist) {
                	
                	//System.out.println(result);
                  	if (result.isDirectory()) {
                		//System.out.println(result.getName());	
                		d_bucket.add(result.getName());
                		
                  	}
                  	else {
                  		System.out.println(result.getName());
                		bucket.add(result.getName());
                		
                  	}
                }
                // add file names to JList
                      		
                
                //index = bucket.indexOf(curfile);
               // JOptionPane.showMessageDialog(null, index);
                 setJList(d_bucket, bucket, curfile);
          	  
          	   
                
                try {
                    readImage = ImageIO.read(new File(fc.getSelectedFile().getPath()));
                	
                    
                } catch (Exception e) {
                    readImage = null;
                }
              //  FitImage_Frame(index, readImage);  

            }
        }
        if(eve.getSource() == fullscreen) {
        	  JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
        }
        if(eve.getSource() == delete) {
      	  JOptionPane.showMessageDialog(null, curfile);
        	delete_file(curfile);
        }
        if(eve.getSource() == previous) {
        	 // JOptionPane.showMessageDialog(null, curfile);
          	
          	previous_file();
          }
        if(eve.getSource() == next) {
        	//  JOptionPane.showMessageDialog(null, curfile);
        	next_file();
          }
    }
    
    private DefaultListModel addElement(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete_file(String curfile) {
    	

		  //JOptionPane.showMessageDialog(null, curfile);
		  
    	if (index == bucket.size()) {
            System.out.println("The end of the list");
    	} else {
    		
				 	   File file = new File(path+"\\"+curfile);
				        
				        if( file.exists() ){
				            if(file.delete()){
				                System.out.println("파일삭제 성공");
				                index = bucket.indexOf(curfile);
				            	bucket.remove(index);
							 	
							 	UpdateJLabel(index);
							 	curfile = bucket.get(index);
							 								 	  
						//		  JOptionPane.showMessageDialog(null, curfile);
								   
							 	UpdateJList(d_bucket, bucket, curfile);
								 				    
							 	
				            }else{
				                System.out.println("파일삭제 실패");
				            }
				        }else{
				            System.out.println("파일이 존재하지 않습니다.");
				        }
						  
			        
				 	
    	}
      	  
    }
    public void resize_image() {
  	   JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
        
     }
     
     

	    
     
     public void keyTyped(KeyEvent e) {
        
     }
     
     public void next_file() {
    	 index = bucket.indexOf(curfile);
      	// JOptionPane.showMessageDialog(null, curfile);
	         	
     		if (index == bucket.size()-1) {
                 System.out.println("The end of the list");
     		} else {
		    
      	 
							         	 if (fullscreenmode == true) {         	
										             	//    	JOptionPane.showMessageDialog(null, cb.getName());
											         	  	UpdateJLabel2(index+1);
											         	  	curfile = bucket.get(index+1);
											         	  	scrollList.setSelectionInterval(index+dir_size,  index+dir_size);
											           	 //scrollList.setSelectionInterval(9,  9);
											           	scrollList.ensureIndexIsVisible(index);

											         	
											         	
							         	 } else {
							         		// JOptionPane.showMessageDialog(null, index);
							         		scrollList.setSelectionInterval(index+1+dir_size,  index+1+dir_size);
							         		 UpdateJLabel(index);
							         		curfile = bucket.get(index);

							         	  	
							           	 //scrollList.setSelectionInterval(9,  9);
							         	 // 	scrollList.ensureIndexIsVisible(index);
							         	 }
     		}
     }
     public void previous_file() {

         index = bucket.indexOf(curfile);
     	 if (index >=1) {
     		 UpdateJLabel(index-1); 
     	 }
           
     }    
     /** Handle the key-pressed event from the text field. */
     public void keyPressed(KeyEvent e) {

         
         if(e.getKeyCode()==KeyEvent.VK_SPACE){
        	  
         //	JOptionPane.showMessageDialog(null, keyString);
        	 next_file();
         }
         
         
         if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
 		        //JOptionPane.showMessageDialog(null, keyString);
         	 previous_file();
         }
         if (e.getKeyCode() == KeyEvent.VK_D) {
		     //JOptionPane.showMessageDialog(null, curfile);
        	 delete_file(curfile);
        	 
         }

         if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) {
		     // JOptionPane.showMessageDialog(null, keyString);
        	 double w,h;
        	 Icon icon = jl.getIcon();
        	 h = icon.getIconHeight()*1.05;
             //double w = readImage.getWidth()*1.1;
              w = icon.getIconWidth()*1.05;
              Image dimg = readImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
 	         
 	         jl.setIcon(new ImageIcon(dimg));
         }
         
         if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
		  		    double w,h;
		  		    Icon icon = jl.getIcon();
		  		    h = icon.getIconHeight()*0.95;
            
             w = icon.getIconWidth()*0.95;
             Image dimg = readImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
	         
	         jl.setIcon(new ImageIcon(dimg));
         }
         
         
         if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown() == true) {   // for full screen picture
        	 if (fullscreenmode == false) {
        		 fullscreenmode = true;
        		 this.setVisible(false); 
        	 } else {
        		 fullscreenmode = false;
        	 }
        	 
 	        //JFrame frame = new JFrame("TitleLessJFrame");
        	 frame = new JFrame("TitleLessJFrame");
 	        //frame.getContentPane().add(new JLabel(" HEY!!!"));
 	        frame.setUndecorated(true);
 	         	        
 	        frame.setVisible(true);
 	        
 	        frame.setExtendedState(MAXIMIZED_BOTH);
 	      
 	        //frame.requestFocus();
 	       
 	        //add key listener
 	        frame.addKeyListener(this);
 	        
 	        frame.setLayout(new GridBagLayout()); 
 	        fjl = new JLabel();
 	        //jl.setText("Left-Bottom");
 	        //jl.setHorizontalTextPosition(JLabel.LEFT);
 	        fjl.setVerticalTextPosition(JLabel.CENTER);
 	        //jl.setBorder(border);
 	        fjl.setHorizontalTextPosition(JLabel.CENTER);
 	        frame.add(fjl);
 	     
	        try {
				readImage = ImageIO.read(new File(path+"\\"+curfile));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        //resize image fit to screen
	        // if height is bigger than width
	        int ratio;
	        int h = readImage.getWidth();
            //double w = readImage.getWidth()*1.1;
            int w = readImage.getHeight();
           
	        ratio = w/h;
	        
	     	         
	        // fjl.setIcon(new ImageIcon(dimg));
	        fjl.setIcon(new ImageIcon(readImage));
	        //add key listener
 	        fjl.addKeyListener(this);
         }
         
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
         	/*
        	 Object obj = e.getSource();
         	if (obj instanceof JFrame) {
         	    JFrame cb = (JFrame)obj;
         	    cb.dispose();
         	}
         	*/
        	 if (fullscreenmode == true) {
        		 //frame.setVisible(false);
        		 frame.dispose();
        	 }
         	 this.setVisible(true); 
         	 
 	        
         }
         
         
         
     }

     /** Handle the key-released event from the text field. */
     public void keyReleased(KeyEvent e) {
        
     }
     
     
     @Override
     public void mouseDragged(MouseEvent e) {
     	//  JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
     	//  int x = e.getPoint().x - offset.x;
        //   int y = e.getPoint().y - offset.y;
         //  System.out.print (x+"\n");
    	  //setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    	 setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
           jl.setLocation(jlp.x+e.getXOnScreen()-offset.x, jlp.y+e.getYOnScreen()-offset.y);
         
     }
     @Override
     public void mouseWheelMoved(MouseWheelEvent e)
     {
    	 setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
    	 Icon icon = jl.getIcon();
    	 double w,h;
     	//  JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
    	 if (e.getWheelRotation() < 0) {
    		 
             h = icon.getIconHeight()*1.05;
             //double w = readImage.getWidth()*1.1;
              w = icon.getIconWidth()*1.05;
            
             
           } else {
            // System.out.println("Down... " + e.getWheelRotation());
             
              h = icon.getIconHeight()*0.95;
          
	          w = icon.getIconWidth()*0.95;
           }
	    	
	         //BufferedImage dimg = new BufferedImage(icon.getIconWidth(),
	         //                icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
	        
	         Image dimg = readImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
	         
	         jl.setIcon(new ImageIcon(dimg));
     
     }
   
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		  
	    	
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JComponent jc = (JComponent) e.getSource();
		
		if (jc.getClass().getName().contains("JLabel")){
		  //System.out.println(jc.getClass().getName());
			jl.requestFocus();
			 scrollList.clearSelection();
			  jl.requestFocus();
		}
		 if (e.getClickCount() == 2) {
			    System.out.println("double clicked");
			    jl.requestFocus();
			  }
		
	 
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		  setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		//  scrollList.clearSelection();
		 // UpdateJLabel(index);
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		 
        jlp = jl.getLocation(); 
		offset = e.getLocationOnScreen();
         
       //  System.out.print (jlp.x+"\n");
         
         
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		 if (e.isPopupTrigger()) {
        	 popup.show(e.getComponent(), e.getX( ), e.getY());
        
           }
		 
	}
	
	  void status_bar(int index)
	    {
	  	  File f = new File(path+"\\"+bucket.get(index));
		  int f_size;
		  String s_size;
		  
		  if (f.length() >1024) {
			  f_size = (int) (f.length() / 1024);
			  s_size = String.valueOf(f_size) + "KB";
		  } else if (f.length() >1024*1024) {
			  f_size = (int) (f.length() / 1024*1024);
			  s_size = String.valueOf(f_size) + "MB";
		  }  
		  else {
			  f_size = (int) f.length();
			  s_size = String.valueOf(f_size) + "Bytes";
		  }
			  
		  
		 // JLabel status = new JLabel(f_size);
		 // JOptionPane.showMessageDialog(null, "ddd");
		  try {
				readImage = ImageIO.read(new File(path+"\\"+bucket.get(index)));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        //resize image fit to screen
	        // if height is bigger than width
	        int ratio;
	        int h = readImage.getWidth();
	      //double w = readImage.getWidth()*1.1;
	      int w = readImage.getHeight();
	      
	      status.setText("");
	      
	      status = new JLabel(index+" order file selected "+w+"x"+h+" Size:"+s_size);
		  
		  this.add(status, BorderLayout.SOUTH);
	    }
	   
	
	 //fit the image size to frame
    public void FitImage_Frame(int index, BufferedImage readImage) {
    	Image dimg = null;   
    	  File Fdir = new File(path+"\\"+bucket.get(index));
    	//  JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
  		
      	   
    	       	        
			   	 //jl.setIcon(new ImageIcon(path+"\\"+bucket.get(index)));
			   	   Dimension f_size = this.getContentPane().getSize();
			  	 	int w=0,h=0;
					  	  w          = readImage.getWidth();
						  h         = readImage.getHeight();
					   	         	
						//  JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
					  		
			     	 if (h >f_size.height) {
			
			     	 	  dimg = readImage.getScaledInstance((int) (w*f_size.height/h), (int)f_size.height, Image.SCALE_SMOOTH);
			     	 	  jl.setIcon(new ImageIcon(dimg));
			     	 } else if (w >f_size.width) {
			
			      		// JOptionPane.showMessageDialog(null, h);    
			          	  dimg = readImage.getScaledInstance((int) f_size.width, (int)(h*f_size.width/w), Image.SCALE_SMOOTH);
			          	  jl.setIcon(new ImageIcon(dimg));
			      	 } else 
			      		 {
			      		 	jl.setIcon(new ImageIcon(path+"\\"+bucket.get(index)));
			      		 }
			     //	JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
			  		
      	   	
      	  
    }

	 public void UpdateJLabel(int index) {
	    		        
		 Image dimg = null;   
	     	
	        try {
				readImage = ImageIO.read(new File(path+"\\"+bucket.get(index)));
				//JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
				
				FitImage_Frame(index, readImage);
				//jl.setIcon(new ImageIcon(path+"\\"+bucket.get(index)));
				status_bar(index);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       //JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
			
	       
 }


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
		//int cui = bucket.indexOf(curfile);
		//JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
		
		  if (scrollList.isSelectionEmpty()) {
	           // output.append(" <none>");
	        } else {
	        			
						String selected = scrollList.getSelectedValue().toString();
						//
				     	int cui = bucket.indexOf(selected);
				     	//JOptionPane.showMessageDialog(null, bucket.get(cui));
						
				     	//
				     	
						index = cui;
				    	
						UpdateJLabel(cui);
				    	
				    	status_bar(index);
				    	curfile = bucket.get(index);
	  }
    	// scrollList.setSelectionInterval(cui,  cui);
	}
	
	
	
}

public class ImageViewer {
  
    public static void main(String[] args) {
      
        JMenuBarTest imageviewer = new JMenuBarTest();
    }
}

 
/*
 * TO DO
 * 
 * Add bias saves
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

public class NetSave {
    
    String str = "";
    File file;
    FileWriter writer;
    FileReader reader;
    static ArrayList<ArrayList<double[][]>> netList = new ArrayList<>();
    static ArrayList<int[]> networkShapes = new ArrayList<>();
    static ArrayList<ArrayList<double[]>> biasList = new ArrayList<>();
    static ArrayList<String> commentList = new ArrayList<>();
    static int numNetworks = 0;

    //Defined characters
    char dimensionCharacter = '@';
    char networkCharacter = ':';
    char biasCharacter = '~';
    char commentCharacter = '#';
    char closeCharacter = '$';

    public NetSave(File file) throws IOException {
        this.file = file;
        writer = new FileWriter(file, true);
        reader = new FileReader(file);

        if(!file.exists()) {
            file.createNewFile();
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists");
        }
    }

    public void writeFile(int[] size, String net, String bias, String comment) throws IOException {
        //append size
        writer.append(dimensionCharacter);
        for(int i = 0; i < size.length; i++) {
            writer.append(size[i] + " ");
        }
        writer.append(networkCharacter);

        //append network
        writer.append(net + biasCharacter);

        //append bias
        writer.append(bias + commentCharacter);

        //append comment
        writer.append(comment + closeCharacter);
    }

    public void closeWriter() throws IOException {
        writer.close();
    }

    public void loadStr() throws IOException {
        int data = reader.read();
        numNetworks = 0;
        int currentIndex = 0;

        //load data into one big string
        while(data != -1) {
            
            String phold = "";
            String[] pholdArr;
            

            //@ - dimensions character
            if((char)data == dimensionCharacter) {
                //System.out.println("size");
                data = reader.read();
                numNetworks++;
                System.out.println("added");

                while((char)data != ':') {
                    phold += (char)data;
                    data = reader.read();
                }

                pholdArr = phold.split(" ");
                networkShapes.add(new int[pholdArr.length]);
                for(int i = 0; i < pholdArr.length; i++) {
                    networkShapes.get(currentIndex)[i] = Integer.parseInt(pholdArr[i]);
                }

                phold = "";
            }

            //: - closing dimension character, opening network character
            if((char)data == networkCharacter) {
                //System.out.println("net");
                data = reader.read();

                while((char)data != biasCharacter) {
                    
                    if((char)data != '\n') {
                        phold += (char)data;
                    }
                    data = reader.read();
                }

                pholdArr = phold.split(" ");
                netList.add(new ArrayList<double[][]>());

                int pholdArrInd = 0;
                for(int numArr = 0; numArr < networkShapes.get(currentIndex).length - 1; numArr++) {

                    int numRows = networkShapes.get(currentIndex)[numArr + 1];
                    int numCol = networkShapes.get(currentIndex)[numArr];
                    netList.get(currentIndex).add(new double[numRows][numCol]);

                    for(int rows = 0; rows < numRows; rows++) {
                        for(int columns = 0; columns < numCol; columns++) {
                            netList.get(currentIndex).get(numArr)[rows][columns] = Double.parseDouble(pholdArr[pholdArrInd]);
                            pholdArrInd++;
                        }
                    }
                }
                
                phold = "";
            }

            //~ - closing network character, opening bias character
            if((char)data == biasCharacter) {
                //System.out.println("bias");
                data = reader.read();
                
                while((char)data != commentCharacter) {
                    if((char)data != '\n') {
                        phold += (char)data;
                    }
                    data = reader.read();
                }

                pholdArr = phold.split(" ");
                int pholdArrInd = 0;
                int numRows = networkShapes.get(currentIndex).length - 1;
                ArrayList<double[]> pholdArrBias = new ArrayList<>();
                for(int row = 0; row < numRows; row++) {
                    int numCol = networkShapes.get(currentIndex)[row + 1];
                    pholdArrBias.add(new double[numCol]);
                    for(int col = 0; col < numCol; col++) {
                        pholdArrBias.get(row)[col] = Double.parseDouble(pholdArr[pholdArrInd]);
                        pholdArrInd++;
                    }
                }
                biasList.add(pholdArrBias);
                pholdArrBias.clear();
                phold = "";
            }
        
            //# - closing bias character, opening comment character
            if((char)data == commentCharacter) {
                //System.out.println("comment");
                data = reader.read();

                while((char)data != closeCharacter) {
                    if((char)data != '\n') {
                        phold += (char)data;
                    }
                    data = reader.read();
                }

                commentList.add(phold);

                phold = "";
                currentIndex++;
            }

            data = reader.read();
        
        }     
 
    }

}


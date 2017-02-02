package hexagon;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreateFrame extends JPanel implements ActionListener{

	static int index = 0;

	public JButton createButton(){		
		JButton openButton = new JButton("Select File To Open");
		openButton.addActionListener(this);
		return openButton;
	}

	public int[] arrangeHexagons(String hexagonDesc){
		Map<String, Integer> hexagonMap = new HashMap<String, Integer>();
		List<String> fileLinesList = new ArrayList<String>();
		int hexagonalData[] = new int[7];
		String midHexagonPattern = "";
		String fileLines[] = hexagonDesc.split("\n");
		for (String fileLine : fileLines) {
			fileLinesList.add(fileLine);
		}
		//to get pattern line by line
		int midHexagon=0;
		int midHexagonSize=0;
		for (String pattern : fileLines) {
			//to find center pattern i.e. the pattern with max variations
			String hexagon = pattern.replaceAll("[^a-zA-Z]", ""); 
			char patternArray[] = hexagon.toCharArray();
			String tempString="";
			for (char c : patternArray) {
				if(!tempString.contains(c+"")){
					tempString+=c;
				}	
			}
			//			System.out.println(tempString);
			//			System.out.println(pattern.trim());
			if(tempString.length()> midHexagonSize){
				midHexagonSize = tempString.length();
				midHexagon = Integer.parseInt(pattern.substring(0,pattern.trim().indexOf(" ")));
				midHexagonPattern = pattern;
			}

		}
//		midHexagonPattern=midHexagonPattern.replaceAll("[^a-zA-Z]", "");
		char hexagonalKeys[]=midHexagonPattern.replaceAll("[^a-zA-Z]", "").toCharArray();
		System.out.println(midHexagon);
	//	fileLinesList.remove(midHexagonPattern);
		for (char hexagonalKey : hexagonalKeys) {
			if(hexagonalKey==' ')
				continue;
			Map<String, Integer> hexagon=mapToKeys(fileLinesList, midHexagonPattern);
			System.out.println(hexagon);
		//	hexagonMap.put(hexagonalKey+"",Integer.parseInt(hexagon.replaceAll("[^0-9]", "")));
	//		fileLinesList.remove(hexagon);
			System.out.println("file : "+fileLinesList);
		}
		System.out.println(hexagonMap);
		return hexagonalData;
	}

	private Map<String, Integer> mapToKeys(List<String> fileLinesList, String midHexagonPattern) {		
		String midHexagonPatternOrg = midHexagonPattern;
		List<String> fileLinesListOrg = new ArrayList<String>();
		for (String string : fileLinesList) {
			fileLinesListOrg.add(string);
		}
		fileLinesList.remove(midHexagonPattern);
		midHexagonPattern=midHexagonPattern.replaceAll("[^a-zA-Z]", "");
		char hexagonalKeys[]=midHexagonPattern.toCharArray();
		boolean checkFlag = false;
		int checkCount = 7;
		Map<String, List<String>> hexagonMap = new HashMap<String, List<String>>();
		Map<String, Integer> patternMapping = new HashMap<String, Integer>();

		for (String pattern : fileLinesList) {
			List<String> characterList = new ArrayList<String>();
			String hexagon = pattern.replaceAll("[^a-zA-Z]", "");
			for (char hexagonalKey : hexagonalKeys) {
				if(hexagon.contains(hexagonalKey+"")&&hexagonalKey!=' '&&!characterList.contains(hexagonalKey)){
					characterList.add(hexagonalKey+"");
				}
			}
			hexagonMap.put(pattern, characterList);
		}
		for (String key : hexagonMap.keySet()) {
			int counter = 0;
			for (String key1 : hexagonMap.keySet()) {
				if(hexagonMap.get(key1).size() == 0){
					counter++;
				}
			}
			if(counter == 7 && patternMapping.size() != 6){
				checkFlag = true;
				break;
			}else if(counter == 7 && patternMapping.size() == 6){
				System.out.println("here");
				break;
			}
			String patternKey="";
			int minPatternSize = 10;
			String minSizePattern = "";
			for (String key1 : hexagonMap.keySet()) {
				if(hexagonMap.get(key1).size() < minPatternSize && hexagonMap.get(key1).size() != 0){
					minPatternSize =hexagonMap.get(key1).size(); 
					minSizePattern = key1;
				}
			}
			System.out.println(minSizePattern + " : "+ minPatternSize);
				patternKey = hexagonMap.get(minSizePattern).get(0);
				if(patternKey!="")
					patternMapping.put(patternKey+"", Integer.parseInt(minSizePattern.replaceAll("[^0-9]", "")));
				hexagonMap.get(minSizePattern).clear();
			
				for (String key1 : hexagonMap.keySet()) {
					if(hexagonMap.get(key1).contains(patternKey))
						hexagonMap.get(key1).remove(patternKey);
				}
				checkCount--;	
		}
		if(patternMapping.size() != 6)
			checkFlag=true;
		System.out.println(patternMapping);
		if(checkFlag){
			midHexagonPattern = fileLinesListOrg.get(++index);
			patternMapping=mapToKeys(fileLinesListOrg, midHexagonPattern);
			System.out.println(patternMapping);
		}
		patternMapping.put("mid", Integer.parseInt(midHexagonPatternOrg.replaceAll("[^0-9]", "")));
		return patternMapping;
	}

	public String fileChooser() throws IOException{
		String fileContent="";
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text","txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			fileContent = new Scanner(selectedFile).useDelimiter("\\Z").next();
		}
		return fileContent;
	}
	public void actionPerformed(ActionEvent e) {
		int hexagons[] = new int[7];
		List<String> fileLinesList = new ArrayList<String>();
		Map<String, Integer> hexagonMap = new HashMap<String, Integer>();

		try {
			String fileContent = fileChooser();
			String midHexagonPattern = "";
			String fileLines[] = fileContent.split("\n");
			midHexagonPattern = fileLines[index];
			for (String fileLine : fileLines) {
				fileLinesList.add(fileLine);
			}
			hexagonMap = mapToKeys(fileLinesList, midHexagonPattern);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(e.getSource());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CreateFrame createFrame = new CreateFrame();

		frame.setTitle("DrawPoly");
		frame.setSize(350, 250);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JButton openButton = new JButton("Select File To Open");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createFrame.createButton());
		frame.add(buttonPanel);

		frame.show();
	}
}

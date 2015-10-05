import java.io.*;
import java.lang.String;

public class hillcipher {
	
	public static void main(String[] args) {
		String plainTextName, keyName, line = null;
		int[][] plainTextMatrix;
		int[][] keyMatrix;
		int[] cipherTextMatrix;
		String cipherText = "";
		
		String keyText = "";
		
		String plainText = "";
		int keyN = 0;
		
		
		//store arguments in string
		keyName = args[0];
		plainTextName = args[1];
		
		//read in plaintext
		try {
			FileReader fileRead = new FileReader(plainTextName);
			BufferedReader buffRead = new BufferedReader(fileRead);
			
			while( (line = buffRead.readLine()) != null ){
				plainText += line;
			}
			
			buffRead.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Cannot open plaintext file.");
		}
		catch(IOException ex){
			System.out.println("Error reading plaintext file.");
		}
		
		//read in key text
		try {
			int f = 0;
			FileReader fileRead = new FileReader(keyName);
			BufferedReader buffRead = new BufferedReader(fileRead);
			
			while( (line = buffRead.readLine()) != null ){
				if(f == 0){
					keyN = Integer.parseInt(line);
				}
				else if (f == 1){
					keyText += (line);
				}
				else {
					keyText += (" " + line);
				}
				++f;				
			}
			
			buffRead.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Cannot open key file.");
		}
		catch(IOException ex){
			System.out.println("Error reading key file.");
		}
		
		
		//purge plain text of special characters
		plainText = plainText.replaceAll("[^a-zA-Z0-9]", "");
		plainText = plainText.toLowerCase();
		while( (plainText.length() % keyN) != 0){
			plainText += "x";
		}

		
		//create key matrix
		keyMatrix = new int[keyN][keyN];
		
		//place key string into string array and then to int array
		String[] keyTextArr = keyText.split(" ");
		System.out.println("KeyN = " + keyN);
		int i = 0;
		for(int r = 0; r < keyN; ++r){
			System.out.println("r = " + r);
			int c = 0;
			for(; i < keyTextArr.length; ++i){
				System.out.println("c = " + c);
				keyMatrix[r][c] = Integer.parseInt(keyTextArr[i]);
				System.out.println(keyMatrix[r][c]);
				++c;
				if(c == keyN)
					break;
			}
			++i;
		}
		
		//print key text
		System.out.println("\nKey matrix:\n");
		for(int g = 0; g < keyN; ++g){
			for(int h = 0; h < keyN; ++h){
				System.out.print(keyMatrix[g][h] + " ");
			}
		System.out.print("\n");
		}
		
		//print plaintext
		System.out.println("\nPlain text:\n");
		System.out.println(plainText);
		
		//convert letters into numbers
		plainTextMatrix = new int[plainText.length()/keyN][keyN];

		i = 0;
		for(int r = 0; r < plainText.length()/keyN; ++r){
			int c = 0;
			for(; i < plainText.length(); ++i){
				plainTextMatrix[r][c] = ((int) plainText.charAt(i)) - 97;
				++c;
				if(c == keyN)
					break;
			}
			++i;
		}
		
		//matrix multiplication
		cipherTextMatrix = new int[plainText.length()];
		
		for(int g = 0; g < cipherTextMatrix.length; ++g){
			cipherTextMatrix[g] = 0;
		}
		
		//pt rows
		for(int r = 0; r < plainText.length()/keyN; ++r){
			//key rows
			for(int l = 0; l < keyN; ++l){
				int num = 0;
				//key cols
				int k = 0;
				
				//pt cols
				for(int c = 0; c < keyN; ++c){
					num += plainTextMatrix[r][c] * keyMatrix[l][k];
					++k;
				}
				for(int g = 0; g < cipherTextMatrix.length; ++g){
					if(cipherTextMatrix[g] == 0){
						cipherTextMatrix[g] = num;
						break;
					}
				}
			}
		}
		
		//convert numbers back to letters
		for(int g = 0; g < cipherTextMatrix.length; ++g)
			cipherTextMatrix[g] = cipherTextMatrix[g] % 26;
		
		for(int r = 0; r < cipherTextMatrix.length; ++r){
				cipherText += (char) (cipherTextMatrix[r] + 97);
		}
		
		//print cipher text
		System.out.println("\nCiphertext:\n");
		for(int g = 0; g < cipherText.length(); ++g){
			if(g % 80 == 0)
				System.out.print("\n");
			System.out.print(cipherText.charAt(g));
		}
    }
}
	
	
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class TextFile - Gestao de ficheiros de texto
 */
public class TextFile {
	private BufferedReader fileReader;
	private BufferedWriter fileWriter;
	
	/**
	 * Cria um buffer para leitura
	 * @param fileName nome do ficheiro
	 * @throws IOException
	 */
	public void openTextFileToRead(String fileName) throws IOException
	{
		fileReader = new BufferedReader(new FileReader(fileName));
	}
	
	/**
	 * Cria um buffer para excrita
	 * @param fileName nome do ficheiro
	 * @throws IOException
	 */
	public void openTextFileToWrite(String fileName) throws IOException
	{
		fileWriter = new BufferedWriter(new FileWriter(fileName));
	}
	
	/**
	 * Ler um numero inteiro do buffer
	 * @return int[] result
	 * @throws IOException
	 */
	public int[] readNumberInt() throws IOException
	{
		int[] result = new int[2];
		String st = fileReader.readLine();
		if (st != null) 
		{
			result[0] = 0;
			result[1] = Integer.parseInt(st);
		}
		else
		{
			result[0] = -1;
		}
		return result;
	}
	
	/**
	 * Escrever um numero inteiro no buffer
	 * @param number numero a escrever
	 * @throws IOException
	 */
	public void writeNumberInt(int number) throws IOException
	{
		String st = "";
		st = String.valueOf(number);
		writeLine(st);
	}
	
	/**
	 * Ler uma linha do buffer
	 * @return String line
	 * @throws IOException
	 */
	public String readLine() throws IOException
	{
		return fileReader.readLine();
	}
	
	/**
	 * Escrever uma linha para o buffer
	 * @param line linha a escrever
	 * @throws IOException
	 */
	public void writeLine(String line) throws IOException
	{
		fileWriter.write(line, 0, line.length());
		fileWriter.newLine();
	}
	
	/**
	 * Fechar o buffer de leitura
	 * @throws IOException
	 */
	public void closeRead() throws IOException
	{
		fileReader.close();
	}
	
	/**
	 * Fechar o buffer de escrita
	 * @throws IOException
	 */
	public void closeWrite() throws IOException
	{
		fileWriter.close();
	}
	
	/**
	 * Verifica se um dado ficheiro existe ou nao
	 * @param fileName nome do ficheiro
	 * @return boolean
	 */
	public static boolean fileExists(String fileName)
	{
		File f = new File(fileName);
		if (f.exists()) return true;
		return false;
	}
	
	/**
	 * Eliminar um ficheiro
	 * @param fileName nome do ficheiro
	 */
	public static void deleteFile(String fileName)
	{
		File f = new File(fileName);
		f.delete();
	}
}

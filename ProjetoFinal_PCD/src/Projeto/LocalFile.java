package Projeto;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystemException;


public class LocalFile implements PCDFile {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int NUM_READERS=3;
	
	private String name;
	private int size;
	private File file;
	private WriteLock writeLock;
	private ReadLock readLock;
	
	
	
	
	
	public LocalFile(File file) {
		this.file=file;
		name = file.getName();
		size = (int) file.length();
		writeLock = new WriteLock();
		readLock = new ReadLock();
	}
	
	@Override
	public String read() throws IOException {
		return new String(read(0, length()));
	}
	
	@Override
	public void write(String dataToWrite) throws FileSystemException {
		write(dataToWrite.getBytes(), 0);
	}
	
	public byte[] read(int offset, int length) throws FileSystemException {
		ByteBuffer buffer = null;
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile(getFullPath(), "r");
			FileChannel channel = aFile.getChannel();
			
			if (offset > aFile.length())
				return null;
			
			if (offset + length > aFile.length()) {
				length = (int) aFile.length() - offset;
			}
			
			channel.position(offset);
			buffer = ByteBuffer.allocate(length);
			channel.read(buffer);	
			
		} catch (IOException e) {
			throw new FileSystemException(e.getMessage());
		} finally {
			if (aFile != null)
				try {
					aFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return buffer.array();
	}
	
	private String getFullPath() {
		return file.getAbsolutePath();
	}

	public void write(byte[] dataToWrite, int offset) throws FileSystemException {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile(getFullPath(), "rw");
			FileChannel channel = aFile.getChannel();
			if (offset > aFile.length())
				offset = (int) aFile.length();
			
			ByteBuffer buf = ByteBuffer.wrap(dataToWrite);
			channel.position(offset);
			
			while (buf.hasRemaining()) {
				channel.write(buf);
			}
		} catch (IOException e) {
			throw new FileSystemException(e.getMessage());
		} finally {
			if (aFile != null)
				try {
					aFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean readLock() throws IOException {
		return readLock.tryAcquire();
	}

	@Override
	public boolean writeLock() throws IOException {
		return writeLock.tryLock();
		
	}

	@Override 
	public void readUnlock() throws IOException {
		readLock.release();
		
	}

	@Override
	public void writeUnlock() throws IOException {
		writeLock.unlock();
	}

	@Override
	public boolean exists() throws IOException {
		return file.exists();
	}

	@Override
	public int length() throws FileSystemException, IOException {
		return size;
	}
	
}
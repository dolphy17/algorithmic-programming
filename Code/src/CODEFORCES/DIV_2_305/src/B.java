import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.TreeMap;

/**
 * Created by Shreyans Sheth [bholagabbar] on 5/26/2015 at 10:34 PM using IntelliJ IDEA (Fast IO Template)
 */


class B {
	public static void main(String[] args) throws Exception {
		//System.setIn(new FileInputStream("E:/Shreyans/Documents/Code/CODE/SPOJ/Stdin_File_Read.txt"));
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);
		int n = in.readInt(), m = in.readInt(), q = in.readInt();
		
		boolean grid[][] = new boolean[n][m];
		int[] max = new int[n];
		TreeMap<Integer, HashSet<Integer>> map = new TreeMap<Integer, HashSet<Integer>>();
		
		for (int i = 0; i < n; i++) {
			int cnt = 0, mcnt = 0;
			for (int j = 0; j < m; j++) {
				if (in.readInt() == 1) {
					grid[i][j] = true;
					cnt++;
				} else {
					grid[i][j] = false;
					if (cnt > mcnt) {
						mcnt = cnt;
					}
					
					cnt = 0;
				}
			}
			max[i] = mcnt;
			if (!map.containsKey(mcnt)) {
				HashSet<Integer> x = new HashSet<Integer>();
				x.add(i);
				map.put(max[i], x);
			} else {
				map.get(max[i]).add(i);
			}
		}
		while (q-- > 0) {
			int x = in.readInt() - 1, y = in.readInt() - 1;
			grid[x][y] = !grid[x][y];//flipping
			int ns = 0, ns1 = 0;
			for (int i = 0; i < m; i++) {
				if (grid[x][i]) {
					ns++;
				} else {
					ns1 = Math.max(ns, ns1);
					ns = 0;
				}
			}
			if (ns1 != max[x]) {
				HashSet<Integer> x1 = map.get(max[x]);
				//out.printLine(x1);
				x1.remove(x);
				//out.printLine(map.get(max[x]));
				if (x1.size() == 0) {
					map.remove(max[x]);
				}
				max[x] = ns1;//new value
				
				if (!map.containsKey(max[x])) {
					x1 = new HashSet<Integer>();
					x1.add(max[x]);
					x1.add(x);
					map.put(max[x], x1);
				} else {
					map.get(max[x]).add(x);
				}
				out.printLine(max[x], map.get(max[x]));
			}
			out.printLine(map.lastKey());
		}
		
		{
			out.close();
		}
	}
	
	//FAST IO
	private static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
		
		public int read() {
			if (numChars == -1) {
				throw new InputMismatchException();
			}
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}
		
		public int readInt() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
		
		public String readString() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}
		
		public double readDouble() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') {
				if (c == 'e' || c == 'E') {
					return res * Math.pow(10, readInt());
				}
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') {
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) {
					if (c == 'e' || c == 'E') {
						return res * Math.pow(10, readInt());
					}
					if (c < '0' || c > '9') {
						throw new InputMismatchException();
					}
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}
		
		public long readLong() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
		
		public boolean isSpaceChar(int c) {
			if (filter != null) {
				return filter.isSpaceChar(c);
			}
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
		
		public String next() {
			return readString();
		}
		
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}
	
	private static class OutputWriter {
		private final PrintWriter writer;
		
		public OutputWriter(OutputStream outputStream) {
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
		}
		
		public OutputWriter(Writer writer) {
			this.writer = new PrintWriter(writer);
		}
		
		public void print(Object... objects) {
			for (int i = 0; i < objects.length; i++) {
				if (i != 0) {
					writer.print(' ');
				}
				writer.print(objects[i]);
			}
			writer.flush();
		}
		
		public void printLine(Object... objects) {
			print(objects);
			writer.println();
			writer.flush();
		}
		
		public void close() {
			writer.close();
		}
		
		public void flush() {
			writer.flush();
		}
	}
}
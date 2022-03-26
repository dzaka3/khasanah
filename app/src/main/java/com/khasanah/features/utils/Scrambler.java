package com.khasanah.features.utils;

public class Scrambler
{
	private static Scrambler instance= null;
	private long seed;
	private int[] mix;
	private final String KEY = "D3V3L0PM3NT40123MPC";
	
	private static final char[] hex1 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static final int[] hex2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0,
			0, 0, 10, 11, 12, 13, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 10, 11, 12, 13, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final int[] basic_mix = { 0x73ebcc81, 0x605ffa3f, 0x7f276711, 0x555c3ac, 0x76ed6194, 0x8b5c9453,
			0xa6d2900e, 0xdf9dcf9a, 0xf0035faa, 0x252f91fb, 0xd2e6bc9b, 0x6e28901d, 0x3901f797, 0x9a0cfa71, 0x126e35d2,
			0xf2baf9b4, 0x2203bcf1, 0x30d68e1c, 0xafb6bf50, 0x252424e2, 0x757962c0, 0x4929b551, 0xabe9dba9, 0xcf7a304f,
			0x2b4537e7, 0x2ad86e2d, 0x90dc6fb1, 0x87b06d24, 0xb0182f59, 0xc1118268, 0xb1634531, 0x31e45375, 0x812bc3e,
			0x6c8c5594, 0xed26c447, 0x4d735cd3, 0x6081472f, 0x8f7b0715, 0xfa6dae23, 0xd1b7de88, 0xf0e6fea0, 0xe044c07c,
			0x4485fe7e, 0x4bfc001d, 0xed0499b9, 0x604e0017, 0x816ebf47, 0x4b6ef9a, 0x7ef569cf, 0xa22282d, 0x2a1d5c09,
			0x81b7e0e3, 0x393ab11e, 0x53afa3b7, 0x7c7437cc, 0xff2c62fb, 0xebd9f359, 0x176630ea, 0x75f9214a, 0x4ea1f72,
			0x1e8e670d, 0xc5b6e187, 0x8bd29b84, 0xf031502d, 0x1c3d9074, 0x8b198db3, 0x88cd848d, 0x4d0db5e1, 0xc5597c10,
			0xdfd70ce3, 0xa03784c9, 0x2afebee7, 0xe2441961, 0xaccb27e5, 0xf1c0a2e3, 0xd4f9e032, 0xf84bc9ea, 0xece13715,
			0xd424e323, 0xf00693d6, 0x6e558f1e, 0x483509f7, 0x6803168b, 0x52aa161e, 0x2db0f558, 0x5ff6bce2, 0x9b05341a,
			0xcf9cda22, 0x77c3896d, 0xf6a3f49a, 0x19ce41ad, 0xba6d3a62, 0x28fbaf7, 0x1f45dfed, 0xea71f970, 0x9f522105,
			0x81adb697, 0xae94b107, 0xe407cd4b, 0x5db91b10, 0x9dbc6a15, 0x23cd61ef, 0xa76ca65e, 0xbbb2eeed, 0x226cdbf2,
			0x4deca78e, 0x1714b694, 0x4b5851c, 0xb0a4304, 0x4171fcdf, 0x89c5f76c, 0xcf6dca48, 0xf8149cb, 0xe4de9677,
			0xa893aaee, 0xa3cfb5f9, 0x57b0cee5, 0xe7d8c9be, 0x3a7f79bd, 0xcc64366a, 0x21d95c1, 0x42a0c07c, 0x235f9b70,
			0xcc264646, 0xf31d402f, 0xc4c1be61, 0x5dbffa25, 0xbc9e3705, 0x467ae297, 0x6f959af2, 0x7a30ffc1, 0x7b4e3d0f,
			0x30a40de2, 0x7c8917a4, 0x1fa176da, 0x9898fe3d, 0x3a050bf7, 0x46686ae6, 0xc2c85722, 0x75eebda0, 0x603fb07a,
			0x8d79c3ba, 0xd1012250, 0x66d4ba6d, 0xa215f835, 0x2152b010, 0x114ae628, 0xc8d41185, 0x4efc4509, 0xa5dae516,
			0x93e0d5d8, 0x5675a57c, 0xae3fe1d0, 0xde3a09af, 0xaea014c, 0x13582efc, 0x243a720, 0x4067352b, 0x1d9c5077,
			0xc5768b16, 0xe8b10fb6, 0xcb18e1a8, 0x2c053597, 0xd8d08990, 0xef5662bd, 0x43d728eb, 0x9b6f403f, 0x6f2cca2d,
			0x9d7f1cc3, 0xb807b7fd, 0x81c23395, 0x2a12e157, 0x9c78b23e, 0x64b25f0a, 0xb66a4754, 0xf53f118, 0xa73b2f30,
			0x8dd2609d, 0x37d1d7e, 0xc71cd03c, 0x3a102578, 0x41250b74, 0x81cedce3, 0x2ca39a88, 0xb2ad708c, 0x6433a00e,
			0x7ecba3f, 0x832990be, 0xd18873a0, 0xa8a227a6, 0xea6d20bd, 0x5afd25fd, 0x8bfcc5ea, 0xb9473d2a, 0x18253656,
			0x5ae2eb1e, 0xd5f933a3, 0x9ea8f335, 0x451fa51, 0x6c3dd792, 0x9fabe55f, 0x91e987bd, 0xdb3c51, 0xc69a9ec5,
			0x8f4480df, 0x29b676d5, 0x39281c18, 0xf3373bf4, 0x597882c2, 0x8c13ee32, 0x6235f29c, 0x538c9b61, 0x4218ea35,
			0xdd9c0854, 0x35001764, 0xeba4e944, 0x6f882806, 0x1f9b999a, 0xc73236e4, 0x67735dfe, 0xbd8e1ad1, 0x51760a8a,
			0x2b4dc010, 0xf066d501, 0x938926b2, 0xf887dbd9, 0xec9a0bf0, 0xaafea72f, 0xccaf88dc, 0x948a38f5, 0x6677744b,
			0xdae8cc09, 0xdcaf7634, 0x2e395ac4, 0x894f5213, 0x89e36d2e, 0xd8d877d0, 0xd888791, 0xf3c802ea, 0x50040a71,
			0xa9a38421, 0xea4b98e6, 0x10312555, 0xf3a87f3f, 0xb627b67f, 0x1f8b9aae, 0xabe43a73, 0xc3084447, 0xc084f6bc,
			0xc5fb8c7, 0x6df2d290, 0x9535115f, 0x750f86bd, 0x9673196b, 0x521e4c3c, 0xf28fa536 };

	private Scrambler()
	{
	}

	public static Scrambler getInstance()
	{
		if (instance == null)
			instance= new Scrambler();
		return instance;
	}

	public String decrypt(String s)
	{		
			init(KEY);
			return decrypt0(hex2str(s));		
	}

	private String decrypt0(char[] ac)
	{
		if (ac == null)
			return null;
		int i= ac.length;
		int j= (i - 1) / 2;
		if (i < 1)
			return "";
		int k= 2 * (i + j) + 1;
		int j2= -1;
		char[] ac1 = new char[j];
		seed= ((long) (123 * i) ^ 0x5deece66dL) & 0xffffffffffffL;
		int[] ai = new int[k];
		for (int l= k - 1; l >= 0; l--)
			ai[l]= rnd();
		for (int i1= 0; i1 < i; i1++)
		{
			int k1= ai[++j2] % i;
			int i2= ai[++j2] % i;
			char c= ac[k1];
			ac[k1]= ac[i2];
			ac[i2]= c;
		}
		int l1= j;
		for (int j1= i - 2; j1 >= 0; j1-= 2)
		{
			j2++;
			ac1[--l1]= (char) ((ac[j1] ^ 0xffff) + (0x10000 - ai[++j2]));
		}
		return new String(ac1);
	}

	public String encrypt(String s)
	{
		init(KEY);
		int i= s.length();
		int j= 2 * i + 1;
		if (i < 1)
			return s;
		char[] ac = new char[i];
		char[] ac1 = new char[j];
		s.getChars(0, i, ac, 0);
		seed= ((long) (123 * j) ^ 0x5deece66dL) & 0xffffffffffffL;
		int i1= -1;
		for (int k= 0; k < i; k++)
		{
			ac1[++i1]= (char) (rnd() & 0xffff);
			ac1[++i1]= (char) (ac[k] + rnd() ^ 0xffff);
		}
		ac1[++i1]= (char) (rnd() & 0xffff);
		for (int l= 0; l < j; l++)
		{
			int j1= rnd() % j;
			int k1= rnd() % j;
			char c= ac1[j1];
			ac1[j1]= ac1[k1];
			ac1[k1]= c;
		}
		return str2hex(ac1);
	}

	private char[] hex2str(String s)
	{
		if (s == null)
			return null;
		int i= s.length();
		int j= i / 4;
		char[] ac = new char[i];
		char[] ac1 = new char[j];
		s.getChars(0, i, ac, 0);
		int k= 0;
		int l= -1;
		for (; k < j; k++)
		{
			int i1= hex2[ac[++l]];
			i1= hex2[ac[++l]] + (i1 << 4);
			i1= hex2[ac[++l]] + (i1 << 4);
			i1= hex2[ac[++l]] + (i1 << 4);
			ac1[k]= (char) i1;
		}
		return ac1;
	}

	private void init(String s)
	{
		int i= basic_mix.length;
		mix= new int[i];
		int j= s.length();
		if (j < 1)
			return;
		char[] ac = new char[j];
		s.getChars(0, j, ac, 0);
		for (int k= 0; k < i; k++)
		{
			char c= ac[k % j];
			mix[k]= basic_mix[(k + c) % i] + c;
		}
	}

	private int rnd()
	{
		seed= seed * 0x5deece66dL + 11L & 0xffffffffffffL;
		int i= (int) (seed >>> 16);
		i+= mix[i & 0xff];
		return i >= 0 ? i : -i;
	}

	private String str2hex(char[] ac)
	{
		if (ac == null)
			return null;
		int i= ac.length;
		int j= 4 * i;
		char[] ac1 = new char[j];
		int k= 0;
		int l= -1;
		for (; k < i; k++)
		{
			char c= ac[k];
			ac1[++l]= hex1[c >> 12];
			ac1[++l]= hex1[c >> 8 & 0xf];
			ac1[++l]= hex1[c >> 4 & 0xf];
			ac1[++l]= hex1[c & 0xf];
		}
		return new String(ac1);
	}	

	public static String encryptPassword(String source, String salt){
		return Scrambler.getInstance().encrypt(salt+source);
	}
	
}

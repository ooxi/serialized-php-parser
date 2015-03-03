/**
 * Copyright (c) 2007-2008 Zsolt Szász <zsolt at lorecraft dot com>
 *               2008-2012 Michele Catalano <michele at catalano.de>
 *               2014-2015 ooxi <violetland at mail.ru>
 * 
 *     https://github.com/ooxi/serialized-php-parser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.github.ooxi.phparser;

import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class SerializedPhpParserTest {

	@Test
	public void testParseNull() throws SerializedPhpParserException {
		String input = "N;";
		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
		Object result = serializedPhpParser.parse();
		assertEquals(SerializedPhpParser.NULL, result);
	}

	@Test
	public void testParseInteger() throws SerializedPhpParserException {
		assertPrimitive("i:123;", 123L);
	}

	@Test
	public void testParseFloat() throws SerializedPhpParserException {
		assertPrimitive("d:123.123;", 123.123d);
	}

	@Test
	public void testParseBoolean() throws SerializedPhpParserException {
		assertPrimitive("b:1;", Boolean.TRUE);
	}

	@Test
	public void testParseString() throws SerializedPhpParserException {
		assertPrimitive("s:6:\"string\";", "string");
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testParseArray() throws SerializedPhpParserException {
		String input = "a:1:{i:1;i:2;}";
		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
		Object result = serializedPhpParser.parse();
		assertTrue(result instanceof Map);
		assertEquals(1, ((Map) result).size());
		assertEquals(2L, ((Map) result).get(1L));
	}

	@Test
	public void testParseObject() throws SerializedPhpParserException {
		String input = "O:8:\"TypeName\":1:{s:3:\"foo\";s:3:\"bar\";}";
		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
		Object result = serializedPhpParser.parse();
		assertTrue(result instanceof SerializedPhpParser.PhpObject);
		assertEquals(1, ((SerializedPhpParser.PhpObject) result).attributes.size());
		assertEquals("bar",
			((SerializedPhpParser.PhpObject) result).attributes.get("foo"));

	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testParseComplexDataStructure() throws SerializedPhpParserException {
		String input = "a:2:{i:0;a:8:{s:5:\"class\";O:7:\"MyClass\":1:{s:5:\"pippo\";s:4:\"test\";}i:0;i:1;i:1;d:0.19999998807907104;i:2;b:1;i:3;b:0;i:4;N;i:5;a:1:{i:0;s:42:\"\";\";\";\";\";AäüÖRÜßÃTÍLÇÅ\";\";\";\";\";\";}i:6;O:6:\"Object\":0:{}}i:1;a:8:{s:5:\"class\";O:7:\"MyClass\":1:{s:5:\"pippo\";s:4:\"test\";}i:0;i:1;i:1;d:0.19999998807907104;i:2;b:1;i:3;b:0;i:4;N;i:5;a:1:{i:0;s:42:\"\";\";\";\";\";AäüÖRÜßÃTÍLÇÅ\";\";\";\";\";\";}i:6;O:6:\"Object\":0:{}}}";
		new SerializedPhpParser(input).parse();

		// sample output of a yahoo web image search api call
		input = "a:1:{s:9:\"ResultSet\";a:4:{s:21:\"totalResultsAvailable\";s:7:\"1177824\";s:20:\"totalResultsReturned\";"
			+ "i:2;s:19:\"firstResultPosition\";i:1;s:6:\"Result\";a:2:{i:0;a:10:{s:5:\"Title\";s:12:\"corvette.jpg\";"
			+ "s:7:\"Summary\";s:150:\"bluefirebar.gif 03-Nov-2003 19:02 22k burning_frax.jpg 05-Jul-2002 14:34 169k corvette.jpg "
			+ "21-Jan-2004 01:13 101k coupleblack.gif 03-Nov-2003 19:00 3k\";s:3:\"Url\";"
			+ "s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:8:\"ClickUrl\";"
			+ "s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:10:\"RefererUrl\";"
			+ "s:35:\"http://www.vu.union.edu/~jaquezk/MG\";s:8:\"FileSize\";"
			+ "s:7:\"101.5kB\";s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"768\";"
			+ "s:5:\"Width\";s:4:\"1024\";s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"http://sp1.mm-a1.yimg.com/image/2178288556\";"
			+ "s:6:\"Height\";s:3:\"120\";s:5:\"Width\";s:3:\"160\";}}i:1;a:10:{s:5:\"Title\";"
			+ "s:23:\"corvette_c6_mini_me.jpg\";s:7:\"Summary\";s:48:\"Corvette I , Corvette II , Diablo , Enzo , Lotus\";"
			+ "s:3:\"Url\";s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:8:\"ClickUrl\";"
			+ "s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:10:\"RefererUrl\";"
			+ "s:61:\"http://mik-blog.blogspot.com/2005_03_01_mik-blog_archive.html\";s:8:\"FileSize\";s:4:\"55kB\";"
			+ "s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"518\";s:5:\"Width\";s:3:\"700\";"
			+ "s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"http://sp1.mm-a2.yimg.com/image/2295545420\";"
			+ "s:6:\"Height\";s:3:\"111\";s:5:\"Width\";s:3:\"150\";}}}}}";
		Map results = (Map) new SerializedPhpParser(input).parse();
		assertEquals(2,
			((Map) ((Map) results.get("ResultSet")).get("Result")).size());
	}

	@Test
	public void testParseStructureWithSpecialChars() throws SerializedPhpParserException {
		String input = "a:1:{i:0;O:9:\"albumitem\":19:{s:5:\"image\";O:5:\"image\":12:{s:4:\"name\";"
			+ "s:26:\"top_story_promo_transition\";s:4:\"type\";s:3:\"png\";s:5:\"width\";i:640;"
			+ "s:6:\"height\";i:212;s:11:\"resizedName\";s:32:\"top_story_promo_transition.sized\";"
			+ "s:7:\"thumb_x\";N;s:7:\"thumb_y\";N;s:11:\"thumb_width\";N;s:12:\"thumb_height\";N;"
			+ "s:9:\"raw_width\";i:900;s:10:\"raw_height\";i:298;s:7:\"version\";i:37;}s:9:\"thumbnail\";O:5:\"image\":12:{s:4:\"name\";"
			+ "s:32:\"top_story_promo_transition.thumb\";s:4:\"type\";s:3:\"png\";s:5:\"width\";i:150;s:6:\"height\";"
			+ "i:50;s:11:\"resizedName\";N;s:7:\"thumb_x\";N;s:7:\"thumb_y\";N;s:11:\"thumb_width\";"
			+ "N;s:12:\"thumb_height\";N;s:9:\"raw_width\";i:150;s:10:\"raw_height\";i:50;s:7:\"version\";i:37;}s:7:\"preview\";"
			+ "N;s:7:\"caption\";s:6:\"sup�rb\";s:6:\"hidden\";N;s:9:\"highlight\";b:1;s:14:\"highlightImage\";O:5:\"image\":12:{s:4:\"name\";"
			+ "s:36:\"top_story_promo_transition.highlight\";s:4:\"type\";s:3:\"png\";s:5:\"width\";i:150;s:6:\"height\";i:50;"
			+ "s:11:\"resizedName\";N;s:7:\"thumb_x\";N;s:7:\"thumb_y\";N;s:11:\"thumb_width\";N;s:12:\"thumb_height\";N;s:9:\"raw_width\";"
			+ "i:150;s:10:\"raw_height\";i:50;s:7:\"version\";i:37;}s:11:\"isAlbumName\";N;s:6:\"clicks\";N;s:8:\"keywords\";s:0:\"\";"
			+ "s:8:\"comments\";N;s:10:\"uploadDate\";i:1196339460;s:15:\"itemCaptureDate\";i:1196339460;s:8:\"exifData\";N;s:5:\"owner\";"
			+ "s:20:\"1156837966_352721747\";s:11:\"extraFields\";a:1:{s:11:\"Description\";s:0:\"\";}s:4:\"rank\";N;s:7:\"version\";i:37;s:7:\"emailMe\";N;}}";
		@SuppressWarnings("rawtypes")
		Map results = (Map) new SerializedPhpParser(input, false).parse();
		assertTrue(results.toString().indexOf("sup�rb") > 0);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testAcceptedAttributeNames() throws SerializedPhpParserException {
		// sample output of a yahoo web image search api call
		String input = "a:1:{s:9:\"ResultSet\";a:4:{s:21:\"totalResultsAvailable\";s:7:\"1177824\";s:20:\"totalResultsReturned\";"
			+ "i:2;s:19:\"firstResultPosition\";i:1;s:6:\"Result\";a:2:{i:0;a:10:{s:5:\"Title\";s:12:\"corvette.jpg\";"
			+ "s:7:\"Summary\";s:150:\"bluefirebar.gif 03-Nov-2003 19:02 22k burning_frax.jpg 05-Jul-2002 14:34 169k corvette.jpg "
			+ "21-Jan-2004 01:13 101k coupleblack.gif 03-Nov-2003 19:00 3k\";s:3:\"Url\";"
			+ "s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:8:\"ClickUrl\";"
			+ "s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:10:\"RefererUrl\";"
			+ "s:35:\"http://www.vu.union.edu/~jaquezk/MG\";s:8:\"FileSize\";"
			+ "s:7:\"101.5kB\";s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"768\";"
			+ "s:5:\"Width\";s:4:\"1024\";s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"http://sp1.mm-a1.yimg.com/image/2178288556\";"
			+ "s:6:\"Height\";s:3:\"120\";s:5:\"Width\";s:3:\"160\";}}i:1;a:10:{s:5:\"Title\";"
			+ "s:23:\"corvette_c6_mini_me.jpg\";s:7:\"Summary\";s:48:\"Corvette I , Corvette II , Diablo , Enzo , Lotus\";"
			+ "s:3:\"Url\";s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:8:\"ClickUrl\";"
			+ "s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:10:\"RefererUrl\";"
			+ "s:61:\"http://mik-blog.blogspot.com/2005_03_01_mik-blog_archive.html\";s:8:\"FileSize\";s:4:\"55kB\";"
			+ "s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"518\";s:5:\"Width\";s:3:\"700\";"
			+ "s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"http://sp1.mm-a2.yimg.com/image/2295545420\";"
			+ "s:6:\"Height\";s:3:\"111\";s:5:\"Width\";s:3:\"150\";}}}}}";

		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
		serializedPhpParser
			.setAcceptedAttributeNameRegex("ResultSet|totalResultsReturned");
		Object result = serializedPhpParser.parse();
		// available
		assertEquals(2L,
			((Map) ((Map) result).get("ResultSet")).get("totalResultsReturned"));
		// not available
		assertNull(((Map) ((Map) result).get("ResultSet"))
			.get("totalResultsAvailable"));
	}

	@Test
	public void testExceptionWrongArrayLength1() {
		String input = "a:2:{i:1;s:10:\"Test Test!\";}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionWrongArrayLength2() {
		String input = "a:2:{i:1;s:10:\"Test Test!\";i:2;i:22;i:3;d:21378;}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionWrongStringLength1() {
		String input = "a:2:{i:1;s:8:\"Test Test!\";i:2;s:2:\"TT\";}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionWrongStringLength2() {
		String input = "a:2:{i:1;s:11:\"Test Test!\";i:2;s:2:\"TT\";}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionMissingEndAfterString1() {
		String input = "a:2:{i:1;s:10:\"Test Test!\";i:2;s:2:\"TT\"}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionMissingEndAfterString2() {
		String input = "a:2:{i:1;s:10:\"Test Test!\";i:2;s:2:\"TT;}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionMissingEndAfterInteger() {
		String input = "a:2:{i:1;s:10:\"Test Test!\";i:2;i:21387481}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionMissingEndAfterDouble() {
		String input = "a:2:{i:1;s:10:\"Test Test!\";i:2;d:21387481}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testExceptionWrongObjectLength() {
		String input = "O:9:\"TestClass\":2:{s:4:\"Var1\";i:10;}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testBugDefectStringWithSpezialChar() {
		String input = "s:4:\"";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testBug2DefectStringWithSpezialChar() {
		// Wrong Length and not correct ended.
		String input = "s:2:\"Def";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
		// Right length, but missing "; at the end of serialized String
		input = "s:3:\"Def";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
		// Right length, but missing ; at the end of serialized String
		input = "s:3:\"Def\"";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	public void testBugStringWithSpezialChar() throws SerializedPhpParserException {
		String input = "s:4:\"" + '\000' + "\";";
		String expected = "" + '\000';
		assertPrimitive(input, expected);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testBugReference() throws SerializedPhpParserException {
		String input = "a:4:{s:2:\"t1\";s:6:\"Friend\";i:2;i:10;i:3;R:2;i:4;R:3;}";
		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
		Object result = serializedPhpParser.parse();
		assertEquals("Friend", ((Map) result).get("t1"));
		assertEquals(((Map) result).get("t1"), ((Map) result).get(3L));
		assertEquals(((Map) result).get(2L), ((Map) result).get(4L));
	}

	@Test
	public void testBugReferenceOutOfRange() {
		String input = "a:4:{s:2:\"t1\";s:6:\"Friend\";i:2;i:10;i:3;R:2;i:4;R:5;}";
		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testWrongEncodingInputString() throws SerializedPhpParserException {
		// ISO-8859-1 String: HäöüÖÜÄ
		String expected = "H" + '\344' + '\366' + '\374' + '\326' + '\334' + '\304';
		String input = "a:1:{s:3:\"Dat\";s:7:\"" + expected + "\";}";

		assertExceptionSimple(
			"org.github.ooxi.phparser.SerializedPhpParserException", input,
			SerializedPhpParserException.TO_SHORT_STRING);

		SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input,
			false);
		Object result = serializedPhpParser.parse();
		assertEquals(expected, ((Map<String, String>) result).get("Dat"));
	}

	private void assertExceptionSimple(String expectException, String input, int code) {
		try {
			Object result = new SerializedPhpParser(input).parse();
			fail("Expect a Exception! " + result.toString());
		} catch (Exception ex) {
			assertEquals(expectException, ex.getClass().getName());
			if (ex instanceof SerializedPhpParserException) {
				assertEquals(code, ((SerializedPhpParserException) ex).code);
			}
		}
	}

	private void assertExceptionSimple(String expectException, String input) {
		try {
			Object result = new SerializedPhpParser(input).parse();
			fail("Expect a Exception! " + result.toString());
		} catch (Exception ex) {
			assertEquals(expectException, ex.getClass().getName());
		}
	}

	private void assertPrimitive(String input, Object expected) throws SerializedPhpParserException {
		assertEquals(expected, new SerializedPhpParser(input).parse());
	}

}

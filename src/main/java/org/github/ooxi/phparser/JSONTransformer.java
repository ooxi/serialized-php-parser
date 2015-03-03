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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONTransformer {

	@SuppressWarnings("rawtypes")
	public static Object toJSON(Object o) {
		if (o instanceof Map) {
			return arrayToJSON((Map) o);
		} else if (o instanceof SerializedPhpParser.PhpObject) {
			return mapToJSON(((SerializedPhpParser.PhpObject) o).attributes);
		} else if (o == SerializedPhpParser.NULL) {
			return null;
		}
		return o;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static JSONArray arrayToJSON(Map o) {
		JSONArray a = new JSONArray();
		for (Object obj : o.values()) {
			a.add(toJSON(obj));
		}
		return a;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static JSONObject mapToJSON(Map o) {
		JSONObject obj = new JSONObject();
		Map map = o;
		Iterator<Map.Entry> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Entry next = i.next();
			obj.put(next.getKey(), toJSON(next.getValue()));
		}
		return obj;
	}
}

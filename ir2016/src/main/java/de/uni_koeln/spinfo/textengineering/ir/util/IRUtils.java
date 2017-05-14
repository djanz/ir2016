package de.uni_koeln.spinfo.textengineering.ir.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_koeln.spinfo.textengineering.ir.model.IRDocument;
import de.uni_koeln.spinfo.textengineering.ir.preprocess.Preprocessor;
import de.uni_koeln.spinfo.textengineering.ir.ranked.RankedRetrieval;
import de.uni_koeln.spinfo.textengineering.ir.ranked.VectorComparison;

public final class IRUtils {

	/**
	 * In der IR-Logik können Termfrequenzen (tf) direkt beim Indexieren
	 * gesammelt werden. Ein Beispiel ist der PositionalIndex, tf entspricht
	 * dort einfach der Länge der Positionslisten. In unserem Beispiel gehen wir
	 * davon aus, dass diese Positionslisten nicht zur Verfügung stehen, d.h.
	 * wir müssen uns die tf-Werte vorberechnen und für bequemeren Zugriff in
	 * einer Map ablegen.
	 * 
	 * @param content
	 * @return
	 */
	public static Map<String, Integer> computeTf(String content) {
		Map<String, Integer> termMap = new HashMap<String, Integer>();
		List<String> tokens = new Preprocessor().tokenize(content);
		for (String token : tokens) {
			Integer tf = termMap.get(token);
			if (tf == null) {
				tf = 1;
			} else {
				tf = tf + 1;
			}
			termMap.put(token, tf);
		}
		return termMap;
	}

	/**
	 * Die Cosinus-Ähnlichkeit dieses Werks zu einer query. Die eigentliche
	 * Ähnlichkeitsberechnung delegieren wir an eine Vergleichstrategie,
	 * implementiert in der Klasse VectorComparison.
	 * 
	 * @param query
	 *            document
	 * @param index
	 * 
	 * @return Die Cosinus-Ähnlichkeit dieses Dokuments zu einer query.
	 */
	public static Double similarity(IRDocument queryDoc, IRDocument refDoc, RankedRetrieval index) {
		List<Double> queryVector = queryDoc.computeVector(index);
		List<Double> docVector = refDoc.computeVector(index);
		double sim = VectorComparison.compare(queryVector, docVector);
		return sim;
	}

}
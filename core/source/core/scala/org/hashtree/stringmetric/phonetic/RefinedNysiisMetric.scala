package org.hashtree.stringmetric.phonetic

import org.hashtree.stringmetric.{ FilterableStringMetric, StringFilter, StringMetric }
import org.hashtree.stringmetric.filter.StringFilterDelegate

/** An implementation of the refined NYSIIS [[org.hashtree.stringmetric.StringMetric]]. */
object RefinedNysiisMetric extends StringMetric with FilterableStringMetric {
	type CompareReturn = Boolean

	override def compare(charArray1: Array[Char], charArray2: Array[Char])
		(implicit stringFilter: StringFilter): Option[CompareReturn] = {

		val unequal = (c1: Char, c2: Char) => {
			val lc1 = c1.toLower
			val lc2 = c2.toLower

			(if (lc1 == 'k') 'c' else lc1) != (if (lc2 == 'k') 'c' else lc2)
		}

		val fca1 = stringFilter.filter(charArray1)
		lazy val fca2 = stringFilter.filter(charArray2)

		if (fca1.length == 0 || !Alphabet.is(fca1.head) || fca2.length == 0 || !Alphabet.is(fca2.head)) None
		else if (unequal(fca1.head, fca2.head)) Some(false)
		else RefinedNysiisAlgorithm.compute(fca1).filter(_.length > 0).flatMap(rny1 =>
			RefinedNysiisAlgorithm.compute(fca2).filter(_.length > 0).map(rny1.sameElements(_))
		)
	}

	override def compare(string1: String, string2: String)
		(implicit stringFilter: StringFilter): Option[CompareReturn] =

		compare(
			stringFilter.filter(string1.toCharArray),
			stringFilter.filter(string2.toCharArray)
		)(new StringFilterDelegate)
}
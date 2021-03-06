package com.rockymadden.stringmetric.cli.phonetic

object refinedsoundexalgorithmSpec extends org.specs2.mutable.SpecificationWithJUnit {
	"refinedsoundexalgorithm main()" should {
		"print phonetic representation with valid dashless argument" in {
			val out = new java.io.ByteArrayOutputStream()

			Console.withOut(out)(refinedsoundexalgorithm.main(Array("--unitTest", "--debug", "abc")))
			out.toString must beEqualTo("a013\n")
			out.reset()

			Console.withOut(out)(refinedsoundexalgorithm.main(Array("--unitTest", "--debug", "1")))
			out.toString must beEqualTo("not computable\n")
		}
		"throw IllegalArgumentException with no dashless argument" in {
			refinedsoundexalgorithm.main(Array("--unitTest", "--debug")) must throwA[IllegalArgumentException]
		}
	}
}

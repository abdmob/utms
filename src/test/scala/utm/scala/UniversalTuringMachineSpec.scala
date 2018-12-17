/*
   Copyright 2018 Abdulla Abdurakhmanov (abdulla@latestbit.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package utm.scala

import org.scalatest.FlatSpec


class UniversalTuringMachineSpec extends FlatSpec {

	import UniversalTuringMachine.dsl._

	"Universal turing machine " should "have correct output tape for increment" in {
		sealed trait States
		case class q0() extends States
		case class qf() extends States

		val machine = new UniversalTuringMachine[States](
			rules = List(
				(q0(), "1", "1", right, q0()),
				(q0(), "B", "1", stay, qf())
			),
			initialState = q0(),
			finalStates = Set(qf()),
			blankSymbol = "B",
			inputTapeVals = Seq("1", "1", "1")
		)

		assert ( machine.run().content === Seq("1","1","1","1") )
	}

	it should "have correct output tape for three-state busy beaver" in {
		sealed trait States
		case class a() extends States
		case class b() extends States
		case class c() extends States
		case class halt() extends States

		val machine = new UniversalTuringMachine[States](
			rules = List(
				(a(), "0", "1", right, b()),
				(a(), "1", "1", left, c()),
				(b(), "0", "1", left, a()),
				(b(), "1", "1", right, b()),
				(c(), "0", "1", left, b()),
				(c(), "1", "1", stay, halt())
			),
			initialState = a(),
			finalStates = Set(halt()),
			blankSymbol = "0",
			inputTapeVals = Seq()
		)

		assert ( machine.run().content === Seq("1","1","1","1","1","1") )
	}

	it should "have correct output tape for 5-state, 2-symbol probable Busy Beaver machine" in {
		sealed trait FiveBeaverStates
		case class FA() extends FiveBeaverStates
		case class FB() extends FiveBeaverStates
		case class FC() extends FiveBeaverStates
		case class FD() extends FiveBeaverStates
		case class FE() extends FiveBeaverStates
		case class FH() extends FiveBeaverStates

		val machine = new UniversalTuringMachine[FiveBeaverStates](
			rules = List(
				(FA(), "0", "1", right, FB()),
				(FA(), "1", "1", left, FC()),
				(FB(), "0", "1", right, FC()),
				(FB(), "1", "1", right, FB()),
				(FC(), "0", "1", right, FD()),
				(FC(), "1", "0", left, FE()),
				(FD(), "0", "1", left, FA()),
				(FD(), "1", "1", left, FD()),
				(FE(), "0", "1", stay, FH()),
				(FE(), "1", "0", left, FA())
			),
			initialState = FA(),
			finalStates = Set(FH()),
			blankSymbol = "0",
			inputTapeVals = Seq(),
			printEveryIter = 100000
		)

		assert ( machine.run().content === Seq("1","1","1","1","1","1") )
	}

}
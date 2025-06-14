package edu.austral.dissis.chess.exam

import edu.austral.dissis.chess.engine.exam.MyGameRunner
import edu.austral.dissis.chess.test.game.GameTester
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class Exam {
    @TestFactory
    fun `required exam tests`(): Stream<DynamicTest> {
        return GameTester(MyGameRunner()).test()
    }
}

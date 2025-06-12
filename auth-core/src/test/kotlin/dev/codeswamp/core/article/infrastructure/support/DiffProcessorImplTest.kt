package dev.codeswamp.core.article.infrastructure.support

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class DiffProcessorImplTest {
    private val processor = DiffProcessorImpl()

    @Test
    fun calculateDiff() {
        val origin = "this is origin"
        val change = "this is change"

        println(processor.calculateDiff(origin, change))

        assertEquals(
            """
            |--- 
            |+++ 
            |@@ -1,1 +1,1 @@
            |-this is origin
            |+this is change
        """.trimMargin(), processor.calculateDiff(origin, change)
        )
    }

    @Test
    fun buildFullContent() {
        val diffList: MutableList<String> = mutableListOf()

        val origin = ""
        diffList.add(origin)//snapshot
        val next = "this is first"

        diffList.add(processor.calculateDiff(origin, next)!!)

        val next2 = "this is first, changed"

        diffList.add(processor.calculateDiff(next, next2)!!)

        val next3 = "this is first, changed, second"

        diffList.add(processor.calculateDiff(next2, next3)!!)

        val next4 = "this is first"

        diffList.add(processor.calculateDiff(next3, next4)!!)

        val next5 = "this is first, changed, third"

        diffList.add(processor.calculateDiff(next4, next5)!!)

        val restored = processor.buildFullContent(origin, diffList)
        assertEquals(next5, restored)
    }
}
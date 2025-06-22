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

    @Test
    fun applyDiffTest() {
        val content = "가나다라마바사아자챀타파하\nㅁㅇㅁ나엄너ㅔ뭎체ㅜ제푸메재두패ㅔ전에ㅐㅓ레ㅐㅓ"
        val content2 = "가나다라마바사아자챀타파하\nㅁㅇㅁ나엄너ㅔ뭎체ㅜ제푸메재두패레ㅐㅓ"

        val diff = processor.calculateDiff(content, content2)

        assertEquals(content2, processor.applyDiff(content, diff))
    }
}
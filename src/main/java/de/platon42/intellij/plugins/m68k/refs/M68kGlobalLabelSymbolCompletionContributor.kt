package de.platon42.intellij.plugins.m68k.refs

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import de.platon42.intellij.plugins.m68k.M68kIcons
import de.platon42.intellij.plugins.m68k.psi.M68kTypes
import de.platon42.intellij.plugins.m68k.psi.utils.M68kLookupUtil

class M68kGlobalLabelSymbolCompletionContributor : CompletionContributor() {

    companion object {
        val REGS = listOf(
            "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7",
            "a0", "a1", "a2", "a3", "a4", "a5", "a6", "sp",
            "pc"
        )
        val REGSET = REGS.toSet()
        val REGISTER_SUGGESTIONS: List<LookupElement> =
            REGS.map { PrioritizedLookupElement.withPriority(LookupElementBuilder.create(it).withIcon(M68kIcons.REGISTER).withBoldness(true), 2.0) }
    }

    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(M68kTypes.SYMBOL), object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                resultSet.addAllElements(REGISTER_SUGGESTIONS)
                val prefix = resultSet.prefixMatcher.prefix.lowercase()
                if (!(prefix == "a" || prefix == "d" || REGSET.contains(prefix))) {
                    resultSet.addAllElements(M68kLookupUtil.findAllGlobalLabels(parameters.originalFile.project).map(LookupElementBuilder::createWithIcon))
                    resultSet.addAllElements(M68kLookupUtil.findAllSymbolDefinitions(parameters.originalFile.project).map(LookupElementBuilder::createWithIcon))
                }
            }
        })
    }
}

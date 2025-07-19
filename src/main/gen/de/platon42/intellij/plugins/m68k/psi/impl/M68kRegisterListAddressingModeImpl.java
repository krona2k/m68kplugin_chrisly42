// This is a generated file. Not intended for manual editing.
package de.platon42.intellij.plugins.m68k.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.platon42.intellij.plugins.m68k.asm.Register;
import de.platon42.intellij.plugins.m68k.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class M68kRegisterListAddressingModeImpl extends M68kAddressingModeImpl implements M68kRegisterListAddressingMode {

    public M68kRegisterListAddressingModeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull M68kVisitor visitor) {
        visitor.visitRegisterListAddressingMode(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof M68kVisitor) accept((M68kVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<M68kRegister> getRegisterList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kRegister.class);
    }

    @Override
    @NotNull
    public List<M68kRegisterRange> getRegisterRangeList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kRegisterRange.class);
    }

    @Override
    @NotNull
    public Set<Register> getRegisters() {
        return M68kPsiImplUtil.getRegisters(this);
    }

}

// Generated from AVM.g4 by ANTLR 4.7.1
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AVMParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AVMVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AVMParser#assembly}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssembly(AVMParser.AssemblyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Push}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPush(AVMParser.PushContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Pop}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPop(AVMParser.PopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Add}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(AVMParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Addi}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddi(AVMParser.AddiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Move}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMove(AVMParser.MoveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(AVMParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Sub}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSub(AVMParser.SubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(AVMParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Div}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiv(AVMParser.DivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code And}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(AVMParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(AVMParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transfer}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransfer(AVMParser.TransferContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Equal}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(AVMParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Notequal}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotequal(AVMParser.NotequalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Greater}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreater(AVMParser.GreaterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterOrEq}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterOrEq(AVMParser.GreaterOrEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Less}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLess(AVMParser.LessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessOrEq}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessOrEq(AVMParser.LessOrEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StoreW}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreW(AVMParser.StoreWContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LoadW}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadW(AVMParser.LoadWContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Loadi}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadi(AVMParser.LoadiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Branch}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranch(AVMParser.BranchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BranchQ}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchQ(AVMParser.BranchQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BranchLessQ}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchLessQ(AVMParser.BranchLessQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Label}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(AVMParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpAL}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpAL(AVMParser.JumpALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpReg}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpReg(AVMParser.JumpRegContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(AVMParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Halt}
	 * labeled alternative in {@link AVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHalt(AVMParser.HaltContext ctx);
}
// Generated from AssetLan.g4 by ANTLR 4.4
package parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AssetLanParser}.
 */
public interface AssetLanListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#ret}.
	 * @param ctx the parse tree
	 */
	void enterRet(@NotNull AssetLanParser.RetContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#ret}.
	 * @param ctx the parse tree
	 */
	void exitRet(@NotNull AssetLanParser.RetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code baseExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBaseExp(@NotNull AssetLanParser.BaseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code baseExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBaseExp(@NotNull AssetLanParser.BaseExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#move}.
	 * @param ctx the parse tree
	 */
	void enterMove(@NotNull AssetLanParser.MoveContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#move}.
	 * @param ctx the parse tree
	 */
	void exitMove(@NotNull AssetLanParser.MoveContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#dec}.
	 * @param ctx the parse tree
	 */
	void enterDec(@NotNull AssetLanParser.DecContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#dec}.
	 * @param ctx the parse tree
	 */
	void exitDec(@NotNull AssetLanParser.DecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBinExp(@NotNull AssetLanParser.BinExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBinExp(@NotNull AssetLanParser.BinExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(@NotNull AssetLanParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(@NotNull AssetLanParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#initcall}.
	 * @param ctx the parse tree
	 */
	void enterInitcall(@NotNull AssetLanParser.InitcallContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#initcall}.
	 * @param ctx the parse tree
	 */
	void exitInitcall(@NotNull AssetLanParser.InitcallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBoolExp(@NotNull AssetLanParser.BoolExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBoolExp(@NotNull AssetLanParser.BoolExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull AssetLanParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull AssetLanParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#adec}.
	 * @param ctx the parse tree
	 */
	void enterAdec(@NotNull AssetLanParser.AdecContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#adec}.
	 * @param ctx the parse tree
	 */
	void exitAdec(@NotNull AssetLanParser.AdecContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull AssetLanParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull AssetLanParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCallExp(@NotNull AssetLanParser.CallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCallExp(@NotNull AssetLanParser.CallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNotExp(@NotNull AssetLanParser.NotExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNotExp(@NotNull AssetLanParser.NotExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(@NotNull AssetLanParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(@NotNull AssetLanParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(@NotNull AssetLanParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(@NotNull AssetLanParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#transfer}.
	 * @param ctx the parse tree
	 */
	void enterTransfer(@NotNull AssetLanParser.TransferContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#transfer}.
	 * @param ctx the parse tree
	 */
	void exitTransfer(@NotNull AssetLanParser.TransferContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(@NotNull AssetLanParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(@NotNull AssetLanParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIdExp(@NotNull AssetLanParser.IdExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIdExp(@NotNull AssetLanParser.IdExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(@NotNull AssetLanParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(@NotNull AssetLanParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull AssetLanParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull AssetLanParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterValExp(@NotNull AssetLanParser.ValExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitValExp(@NotNull AssetLanParser.ValExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNegExp(@NotNull AssetLanParser.NegExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link AssetLanParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNegExp(@NotNull AssetLanParser.NegExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#ite}.
	 * @param ctx the parse tree
	 */
	void enterIte(@NotNull AssetLanParser.IteContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#ite}.
	 * @param ctx the parse tree
	 */
	void exitIte(@NotNull AssetLanParser.IteContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssetLanParser#asset}.
	 * @param ctx the parse tree
	 */
	void enterAsset(@NotNull AssetLanParser.AssetContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssetLanParser#asset}.
	 * @param ctx the parse tree
	 */
	void exitAsset(@NotNull AssetLanParser.AssetContext ctx);
}
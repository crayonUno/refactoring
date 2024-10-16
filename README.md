# 《重构：改善代码的即有设计》
> 需求的变化使重构变的必要。如果一段代码能够正常工作，并且不会再被修改，
> 那么完全可以不去重构它。
## Start
基于瀑布型代码结构的示例程序，进行重构。

原则
1. 如果你要给程序添加一个特性，但发现代码因缺乏良好的结构而不易
于进行更改，那就先重构那个程序，使其比较容易添加该特性，然后再添加该
特性。
2. 在进行重构前，要有一组可靠的测试集合。
3. 做完一次修改就运行测试，这样在 我真的犯了错时，只需要考虑一个很小的改动范围，
   这使得查错与修复问题易如 反掌。这就是重构过程的精髓所在：
   小步修改，每次修改后就运行测试。如果我改动了太多东西，
   犯错时就可能陷入麻烦的调试，并为此耗费大把时间。小步修
   改，以及它带来的频繁反馈，正是防止混乱的关键。
4. 重构后提交代码，轻松回滚

继续优化 amountFor 方法，perf 变量来自循环，会一直改变。play 变量来说 perf 计算而来，
所以考虑去除（创建了很多具有局部作用域的临时变量），
1. 去除方式：**查询(创建函数)取代临时变量, *内联 play 变量***
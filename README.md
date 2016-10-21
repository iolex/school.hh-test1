# school.hh-test1
Тропический остров
Предположим, в один прекрасный день вы оказались на острове прямоугольный формы.
Ландшафт этого острова можно описать с помощью целочисленной матрицы размером MxN, каждый элемент которой задаёт высоту соответствующей области острова над уровнем моря.

К примеру, вот небольшой остров размером 3x3:
4 5 4
3 1 5
5 4 1

В сезон дождей остров полностью заливает водой и в низинах скапливается вода. Низиной будем считать такую область острова, клетки которой граничат с клетками, большими по высоте. При этом диагональные соседи не учитываются, а уровень моря принимается за 0. В приведённом выше примере на острове есть только одна низина — это клетка со значением 1 в середине острова (она граничит с клетками высотой 3, 5, 5 и 4).

Таким образом, после дождя высота клеток острова изменится и станет следующей:
4 5 4
3 3 5
5 4 1

Мы видим что в данном примере высота низины изменилась с 1 до 3, после чего вода начала переливаться на соседние клетки, а затем — в море. Общий объём воды, скопившейся на острове — 2 кубические клетки.

Вот пример посложнее:

5 3 4 5
6 2 1 4
3 1 1 4
8 5 4 3

После дождя карта острова принимает следующую форму:

5 3 4 5
6 3 3 4
3 3 3 4
8 5 4 2

Общий объём скопившейся после дождя воды на таком острове составляет целых 7 кубических клеток!

Ваша программа должна читать входные данные из stdin.
В первой строке указывается количество островов K, после чего в следующих строках описываются эти K островов.
В первой строке описания острова задаются его размеры N и M — целые числа в диапазоне [1, 50], разделённые пробелом.
В следующих строках описывается матрица NxM со значениями высот клеток острова, которые могут принимать значения из диапазона [1, 1000].

Вот пример входных данных:

3
3 3
4 5 4
3 1 5
5 4 1
4 4
5 3 4 5
6 2 1 4
3 1 1 4
8 5 4 3
4 3
2 2 2
2 1 2
2 1 2
2 1 2

Ваша программа должна выводить в stdout значения общего объёма воды, скапливающейся на острове после сезона дождей для каждого из входных примеров. Для приведённых выше данных, вывод программы должен быть следующим:

2
7
0

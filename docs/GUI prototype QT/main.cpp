#include "red_black_tree_gui.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Red_Black_Tree_GUI w;
    w.show();

    return a.exec();
}

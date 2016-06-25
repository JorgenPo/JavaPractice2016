#ifndef RED_BLACK_TREE_GUI_H
#define RED_BLACK_TREE_GUI_H

#include <QMainWindow>

namespace Ui {
class Red_Black_Tree_GUI;
}

class Red_Black_Tree_GUI : public QMainWindow
{
    Q_OBJECT

public:
    explicit Red_Black_Tree_GUI(QWidget *parent = 0);
    ~Red_Black_Tree_GUI();

private:
    Ui::Red_Black_Tree_GUI *ui;
};

#endif // RED_BLACK_TREE_GUI_H

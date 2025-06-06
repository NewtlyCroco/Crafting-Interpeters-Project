package org.example;

import java.util.List;


//omggg yess i got this class done and working from the generated files!
//I love that it is coming together a bit more

abstract class Expr {
  static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
    }

    final Expr left;
    final Token operator;
    final Expr right;
  }
  static class Grouping extends Expr {
    Grouping(Expr expression) {
    this.expression = expression;
    }

    final Expr expression;
  }
  static class Literal extends Expr {
    Literal(Object Value) {
    this.Value = Value;
    }

    final Object Value;
  }
  static class Unary extends Expr {
    Unary(Token operator, Expr right) {
    this.operator = operator;
    this.right = right;
    }

    final Token operator;
    final Expr right;
  }
}

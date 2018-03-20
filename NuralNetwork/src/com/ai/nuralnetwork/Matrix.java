/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.nuralnetwork;

/**
 *
 * @author j3rry
 */
class Matrix {
	static final int SIG=1,DSIG=2;
	int rows,cols;
	float data[][];
	Matrix(int rows,int cols) {
    this.rows = rows;
    this.cols = cols;
    this.data = new float[rows][cols];

    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j] = 0;
      }
    }
  }

  static Matrix fromArray(float[] arr) {
    Matrix m = new Matrix(arr.length, 1);
    for (int i = 0; i < arr.length; i++) {
      m.data[i][0] = arr[i];
    }
    return m;
  }

  static Matrix subtract(Matrix a, Matrix b) {
    // Return a new Matrix a-b
    Matrix result = new Matrix(a.rows, a.cols);
    for (int i = 0; i < result.rows; i++) {
      for (int j = 0; j < result.cols; j++) {
        result.data[i][j] = a.data[i][j] - b.data[i][j];
      }
    }
    return result;
  }

  float[][] toArray() {
    float arr[][] =new float[this.rows][this.cols];
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
    	  arr[i][j]=this.data[i][j];
      }
    }
    return arr;
  }

  void randomize() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j] = (float)Math.random() * 2 - 1;
      }
    }
  }

  void add(Matrix n) {
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.cols; j++) {
          this.data[i][j] += n.data[i][j];
        }
      }
    }
  void add(float n) {
     for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.cols; j++) {
          this.data[i][j] += n;
        }
     }
   }


  static Matrix transpose(Matrix matrix) {
    Matrix result = new Matrix(matrix.cols, matrix.rows);
    for (int i = 0; i < matrix.rows; i++) {
      for (int j = 0; j < matrix.cols; j++) {
        result.data[j][i] = matrix.data[i][j];
      }
    }
    return result;
  }

  static Matrix multiply(Matrix a,Matrix b) {
    // Matrix product
    if (a.cols != b.rows) {
      System.out.println("Columns of A must match rows of B.");
    }
    Matrix result = new Matrix(a.rows, b.cols);
    for (int i = 0; i < result.rows; i++) {
      for (int j = 0; j < result.cols; j++) {
        // Dot product of values in col
        float sum = 0;
        for (int k = 0; k < a.cols; k++) {
          sum += a.data[i][k] * b.data[k][j];
        }
        result.data[i][j] = sum;
      }
    }
    return result;
  }

  void multiply(Matrix n) {
      // hadamard product
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.cols; j++) {
          this.data[i][j] *= n.data[i][j];
        }
      }
   }
  void multiply(float n) {
      // Scalar product
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.cols; j++) {
          this.data[i][j] *= n;
        }
      }
    }
 

  void map(int f) {
    // Apply a function to every element of matrix
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        float val = this.data[i][j];
        if(f==SIG)
        	this.data[i][j] = sigmoid(val);
        else
        	this.data[i][j] = dsigmoid(val);
      }
    }
  }

//  Matrix map(Matrix matrix, int f) {
//    Matrix result = new Matrix(matrix.rows, matrix.cols);
//    // Apply a function to every element of matrix
//    for (int i = 0; i < matrix.rows; i++) {
//      for (int j = 0; j < matrix.cols; j++) {
//        float val = matrix.data[i][j];
//        if(f==SIG)
//        	result.data[i][j] = sigmoid(val);
//        else
//        	result.data[i][j] = dsigmoid(val);
//      }
//    }
//    return result;
//  }
  
  float sigmoid(float x) {
	  return (float)(1 / (1 + Math.exp(-x)));
	}

	float dsigmoid(float y) {
	  // return sigmoid(x) * (1 - sigmoid(x));
	  return y * (1 - y);
	}

  void print() {
      System.out.println("------------------");
	  for (int i = 0; i < this.rows; i++) {
	        for (int j = 0; j < this.cols; j++) {
	          System.out.println(this.data[i][j]+"  ");
	        }
	        System.out.println();
	   }
  }
}


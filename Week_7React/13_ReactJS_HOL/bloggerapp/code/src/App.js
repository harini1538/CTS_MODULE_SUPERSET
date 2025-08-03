import React from 'react';
import { books } from './booksData';
import './App.css';

function BookDetails(props) {
  const bookdet = (
    <ul>
      {props.books.map((book) => (
        <div key={book.id}>
          <h3>{book.bname}</h3>
          <h4>{book.price}</h4>
        </div>
      ))}
    </ul>
  );
  return <div className="st2"><h1>Book Details</h1>{bookdet}</div>;
}

function BlogDetails() {
  const content = (
    <div className="v1">
      <h1>Blog Details</h1>
      <h3>React Learning</h3>
      <p><b>Stephen Biz</b></p>
      <p>Welcome to learning React!</p>
      <h3>Installation</h3>
      <p><b>Schwedzeneir</b></p>
      <p>You can install React from npm.</p>
    </div>
  );
  return content;
}

function CourseDetails() {
  const coursedet = (
    <div className="mystyle1">
      <h1>Course Details</h1>
      <p>Angular</p>
      <p>4/5/2021</p>
      <p>React</p>
      <p>6/3/2021</p>
    </div>
  );
  return coursedet;
}

function App() {
  return (
    <div className="main-container">
      <CourseDetails />
      <BookDetails books={books} />
      <BlogDetails />
    </div>
  );
}

export default App;

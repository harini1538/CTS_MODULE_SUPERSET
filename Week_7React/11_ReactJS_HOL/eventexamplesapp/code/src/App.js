import React, { Component } from 'react';

class CurrencyConvertor extends Component {
  constructor() {
    super();
    this.state = {
      amount: '',
      currency: ''
    };
  }

  handleAmountChange = (e) => {
    this.setState({ amount: e.target.value });
  };

  handleCurrencyChange = (e) => {
    this.setState({ currency: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    const { amount, currency } = this.state;
    if (currency.toLowerCase() === 'euro') {
      const result = amount * 80;
      alert(`Converting to Euro Amount is ${result}`);
    } else {
      alert('Please enter Euro as currency to convert.');
    }
  };

  render() {
    return (
      <div>
        <h2 style={{ color: 'green' }}>Currency Convertor!!!</h2>
        <form onSubmit={this.handleSubmit}>
          <label>Amount:</label>
          <input type="text" onChange={this.handleAmountChange} /><br />
          <label>Currency:</label>
          <input type="text" onChange={this.handleCurrencyChange} /><br /><br />
          <button type="submit">Submit</button>
        </form>
      </div>
    );
  }
}

class App extends Component {
  constructor() {
    super();
    this.state = {
      count: 5
    };
  }

  increment = () => {
    this.setState({ count: this.state.count + 1 });
    alert('Hello! Member1');
  };

  decrement = () => {
    this.setState({ count: this.state.count - 1 });
  };

  sayWelcome = (msg) => {
    alert(msg);
  };

  handleClick = () => {
    alert('I was clicked');
  };

  render() {
    return (
      <div style={{ margin: "20px" }}>
        <h3>{this.state.count}</h3>
        <button onClick={this.increment}>Increment</button>
        <button onClick={this.decrement}>Decrement</button>
        <button onClick={() => this.sayWelcome('welcome')}>Say welcome</button>
        <button onClick={this.handleClick}>Click on me</button>

        <br /><br />
        <CurrencyConvertor />
      </div>
    );
  }
}

export default App;

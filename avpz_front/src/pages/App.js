import '../css/App.css';
import Header from './Header';
import Bottom from './Bottom';
import MainArea from './Main';
import React from 'react';

class App extends React.Component {

  render() {
    return (
      <div className="App">
        <div className="header-area">
          <Header />
        </div>
        <div className="main-area">
          <MainArea />
        </div>
        <div className="bottom-area">
          <Bottom />
        </div>
      </div>
    );
  }
}

export default App;

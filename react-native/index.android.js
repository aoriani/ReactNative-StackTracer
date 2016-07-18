import React, { Component } from 'react';
import { AppRegistry, Text } from 'react-native';

class StackTraceSample extends Component {

  componentDidMount() {
  	throw new Error("bar");
  }

  _createTrouble() {
  	throw new Error("foo");
  }

  render() {
  	this._createTrouble();
    return (
      <Text>Hello world!</Text>
    );
  }
}

AppRegistry.registerComponent('StackTraceSample', () => StackTraceSample);
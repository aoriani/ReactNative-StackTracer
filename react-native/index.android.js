import React, { Component } from 'react';
import { AppRegistry, Text } from 'react-native';

class StackTraceSample extends Component {
  render() {
    return (
      <Text>Hello world!</Text>
    );
  }
}

AppRegistry.registerComponent('StackTraceSample', () => StackTraceSample);
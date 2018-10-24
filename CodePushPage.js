/**
 * @author itck_mth
 * @time 2018/10/24 10:40 AM
 * @class describe
 */

import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
} from 'react-native';
import codePush from 'react-native-code-push';

export default class CodePushPage extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return <View style={styles.container}>
            <Text style={styles.text}
                  onPress={() => this.checkUpdate()}
            >
                HELLO CODE PUSH
            </Text>
        </View>
    }

    checkUpdate() {
        codePush.sync({
            updateDialog: {
                appendReleaseDescription: true,
                descriptionPrefix:'\n\n更新内容：\n',
                title:'更新',
                mandatoryUpdateMessage:'',
                mandatoryContinueButtonLabel:'更新',
            },
            mandatoryInstallMode:codePush.InstallMode.IMMEDIATE,
        });
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1

    },
    text: {
        color: 'red',
        fontSize: 20,
        fontWeight: '500'
    }
})
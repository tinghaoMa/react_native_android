/**
 * React Native Android/iOS原生模块开发
 * Author: CrazyCodeBoy
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    Image,
    TextInput,
    Platform,
    TouchableOpacity,
    DeviceEventEmitter
} from 'react-native';
import ImageCrop from './ImageCrop'

const ASPECT_X = "2";
const ASPECT_Y = "1";
export default class index extends Component {
    state = {
        result: '/sdcard/bu_logo.png',
        receive_msg: '',
        nativeMapData: '',
        nativeMapArrayData: '',
    }

    onSelectCrop() {
        let x = this.aspectX ? this.aspectX : ASPECT_X;
        let y = this.aspectY ? this.aspectY : ASPECT_Y;
        ImageCrop.selectWithCrop(parseInt(x), parseInt(y)).then(result => {
            console.log(`result = ${result}`);
            this.setState({
                result: result['imageUrl'] ? result['imageUrl'] : result
            })
        }).catch(e => {
            6
            this.setState({
                result: e
            })
        });
    }

    render() {
        let imgUrl = Platform.OS === 'android' ? 'file://' + this.state.result : this.state.result;
        console.log(`imgUrl = ${imgUrl}`);
        let imageView = this.state.result === "" ? null :
            <Image
                resizeMode='contain'
                style={{height: 200, width: 200}}
                source={{uri: imgUrl}}/>
        return (
            <View style={styles.container}>
                <View
                    style={styles.row}
                >
                    <Text>宽:</Text>
                    <TextInput
                        style={styles.input}
                        defaultValue={ASPECT_X}
                        onChangeText={aspectX => this.aspectX = aspectX}
                    />
                    <Text>比 高:</Text>
                    <TextInput
                        style={styles.input}
                        defaultValue={ASPECT_Y}
                        onChangeText={aspectY => this.aspectY = aspectY}
                    />
                    <Text
                        style={[styles.text, {marginLeft: 20}]}
                        onPress={() => this.onSelectCrop()}
                    >点击裁切图片</Text>
                </View>
                <Text>{imgUrl}</Text>
                {imageView}
                {this.renderButton()}
                <Text style={styles.text}>
                    {this.state.receive_msg}
                </Text>

                <Text style={styles.text}>
                    {this.state.nativeMapData}
                </Text>

                <Text style={styles.text}>
                    {this.state.nativeMapArrayData}
                </Text>
            </View>
        );
    }

    renderButton() {
        return <TouchableOpacity
            onPress={() => {
                this.sayHiToAndroid();
            }}
        >
            <Text style={styles.text}>

                Press Say Hello To Android

            </Text>


        </TouchableOpacity>
    }

    sayHiToAndroid() {
        ImageCrop.sayHelloToAndroid("Hello Android I'am React-Native", (text) => {
            console.log(`text = ${text}`);
            this.setState({
                receive_msg: text
            })
        });
    }


    componentDidMount() {
        this.rnMapListener = DeviceEventEmitter.addListener('nativeCallRNMap',
            (params) => this.onNativeCallRNMap(params))
        this.rnMapArrayListener = DeviceEventEmitter.addListener('nativeCallRNMapArray',
            (params) => this.onNativeCallRNMapArray(params))
    }

    componentWillUnmount() {
        this.rnMapListener && this.rnMapListener.remove();
        this.rnMapArrayListener && this.rnMapArrayListener.remove();
    }

    onNativeCallRNMap(params) {
        console.log('hello onNativeCallRN');
        this.setState({
            nativeMapData: `params.name =${params.name} 
              params.result=${params.result}`
        })
    }

    onNativeCallRNMapArray(params) {
        console.log('hello onNativeCallRN');
        this.setState({
            nativeMapArrayData: `params[0] =${params[0]} 
              params[1]=${params[1]}`
        })
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        marginTop: 20
    },
    row: {
        flexDirection: 'row',
        alignItems: 'center'
    },
    input: {
        borderWidth: 1,
        borderColor: 'red',
        height: 40,
        width: 40
    },
    text: {
        fontSize: 18,
        color: 'green',
        fontWeight: '500'
    }
});

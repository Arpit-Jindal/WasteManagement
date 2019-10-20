#include<string>
jstring Java_com_example_arpit_project_MainActivity_helloWorld(JNIEnv* env,jobject obj){

    return (*env)->NewStringUTF(env,"Hello World");
}
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/dice.proto

package service;

public interface SingleRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.SingleRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Type of dice
   * </pre>
   *
   * <code>int32 type = 1;</code>
   * @return The type.
   */
  int getType();

  /**
   * <pre>
   * Number of dice
   * </pre>
   *
   * <code>int32 num = 2;</code>
   * @return The num.
   */
  int getNum();
}

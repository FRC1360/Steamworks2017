# 2c9c877d21e7a1f08aa83e546f2933780a6c4c9f encoding: utf-8
# @@ 1

########################### DO NOT MODIFY THIS FILE ###########################
#       This file was automatically generated by JRubyFX-fxmlloader on        #
# 2014-12-22 16:27:00 -0500 for ../sfx/plugins/built-in/command/command.fxml
########################### DO NOT MODIFY THIS FILE ###########################

module JRubyFX
  module GeneratedAssets
    class AOTce51af7b129edfccc7586fa5960223cade268b4f
      include JRubyFX
          def __build_via_jit(__local_fxml_controller, __local_namespace, __local_jruby_ext)
      __local_fx_id_setter = lambda do |name, __i|
        __local_namespace[name] = __i
        __local_fxml_controller.instance_variable_set(("@#{name}").to_sym, __i)
      end
(__local_sem_inst = Java.javax.script.ScriptEngineManager.new).setBindings(javax.script.SimpleBindings.new(__local_namespace))
(__local_sem_lang_inst_javascript = __local_sem_inst.getEngineByName("javascript")).setBindings(__local_sem_inst.getBindings(), javax.script.ScriptContext.ENGINE_SCOPE)
build(Java::DashfxControls::DataHBox) do
 setId("base")
 __local_fx_id_setter.call("base", self)
 __local_jruby_ext[:on_root_set].call(self) if __local_jruby_ext[:on_root_set]
 __local_sem_lang_inst_javascript.eval("\n\t\tvar stopp = null\n\t\tvar startt = null;\n\t\tvar swapper = function(ov, old, running) {\n\t\t\tstopp.setVisible(running);\n\t\t\tstartt.setVisible(!running);\n\t\t};\n\t\tvar runnerVp = null;\n\t\tfunction replaced()\n\t\t{\n\t\t\t/*if (runnerVp != null)\n\t\t\t {\n\t\t\t runnerVp[\"removeListener(javafx.beans.value.ChangeListener)\"](swapper);\n\t\t\t }*/\n\t\t\trunnerVp = base.getObservable(\"running\");\n\t\t\trunnerVp[\"addListener(javafx.beans.value.ChangeListener)\"](swapper);\n\t\t\tstartt = start;\n\t\t\tstopp = stop;\n\t\t\tif (typeof runnerVp == \"boolean\")\n\t\t\t{\n\t\t\t\tswapper(null, null, runnerVp.getValue());\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tswapper(null, null, false);\n\t\t\t}\n\t\t}\n\n\t\tfunction run_command()\n\t\t{\n\t\t\trunnerVp.setValue(true);\n\t\t}\n\t\tfunction stop_command()\n\t\t{\n\t\t\trunnerVp.setValue(false);\n\t\t}\n\t")
 getChildren.add(build(Java::DashfxLibControlsFxmlutils::CollapsableHBox) do
  getChildren.add(build(Java::JavafxSceneControl::Button) do
   setId("start")
   __local_fx_id_setter.call("start", self)
   setGraphic(build(Java::JavafxSceneImage::ImageView) do
    setImage(build(FxmlBuilderBuilder, {"url"=>java.net.URL.new(__local_namespace['location'], "media-playback-start.png").to_s}, Java::JavafxSceneImage::Image) do
    end)
    setFitHeight(32.0)
    setFitWidth(32.0)
    setMouseTransparent(true)
    setPickOnBounds(true)
    setPreserveRatio(true)
   end)
   setStyle("-fx-background-color: transparent; -fx-margin: 0; -fx-padding: 0;")
   setContentDisplay(Java::javafx::scene::control::ContentDisplay::GRAPHIC_ONLY)
   setMaxHeight(1.7976931348623157e+308)
   setMnemonicParsing(false)
   setPrefWidth(48.0)
   setText("Run")
   setOnAction(ScriptEventHandler.new("run_command();", __local_sem_lang_inst_javascript))
  end)
  getChildren.add(build(Java::JavafxSceneControl::Button) do
   setId("stop")
   __local_fx_id_setter.call("stop", self)
   setGraphic(build(Java::JavafxSceneImage::ImageView) do
    setImage(build(FxmlBuilderBuilder, {"url"=>java.net.URL.new(__local_namespace['location'], "media-playback-stop.png").to_s}, Java::JavafxSceneImage::Image) do
    end)
    setFitHeight(32.0)
    setFitWidth(32.0)
    setMouseTransparent(true)
    setPickOnBounds(true)
    setPreserveRatio(true)
   end)
   setStyle("-fx-background-color: transparent; -fx-margin: 0; -fx-padding: 0;")
   setContentDisplay(Java::javafx::scene::control::ContentDisplay::GRAPHIC_ONLY)
   setMaxHeight(1.7976931348623157e+308)
   setMnemonicParsing(false)
   setPrefWidth(48.0)
   setText("Stop")
   setOnAction(ScriptEventHandler.new("stop_command();", __local_sem_lang_inst_javascript))
  end)
 end)
 getChildren.add(build(Java::DashfxLibControls::Placeholder) do
  setId("Name")
  __local_fx_id_setter.call("Name", self)
  setControlPath("Label")
  setPropList("name: name")
  setMaxHeight(1.7976931348623157e+308)
  setMaxWidth(1.7976931348623157e+308)
  Java::JavafxSceneLayout::HBox.setHgrow(self, Java::javafx::scene::layout::Priority::ALWAYS)
 end)
 setStyle("/*Intentionally blank*/")
 setAlignment(Java::javafx::geometry::Pos::CENTER_LEFT)
 setDataMode(Java::dashfx::lib::data::DataPaneMode::Nested)
 setOnRegisterRequest(ScriptEventHandler.new("replaced()", __local_sem_lang_inst_javascript))
end
    end

      def hash
        "2c9c877d21e7a1f08aa83e546f2933780a6c4c9f"
      end
      def compiled?
        true
      end
    end
  end
end

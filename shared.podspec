Pod::Spec.new do |spec|
    spec.name                     = 'shared'
    spec.version       = "2.0"
    spec.homepage                 = 'http://google.com'
    spec.source = { :git => "https://github.com/getarchitdhupar/TestCocopodsPublicRepo.git", :tag => "#{spec.version}" }
    spec.authors                  = 'getarchit.dhupar@gmail.com'
    spec.license                  = 'https://opensource.org/licenses/Apache-2.0'
    spec.summary                  = 'This is the description of framework example.'

    spec.vendored_frameworks      = "shared.xcframework"

    spec.ios.deployment_target = '15.0'

    spec.user_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=*simulator*]' => 'arm64' }
    spec.pod_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=*simulator*]' => 'arm64' }

end
